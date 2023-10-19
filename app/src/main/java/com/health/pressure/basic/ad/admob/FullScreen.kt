package com.health.pressure.basic.ad.admob

import android.app.Activity
import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdItem
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.onLoaded
import com.health.pressure.ext.logcat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class FullScreen(val adLoc: AdLocation, val item: AdItem) : BaseAd(adLoc, item) {

    private var ad: Any? = null
    private val adRequest get() = AdRequest.Builder().build()

    override fun loadAd(context: Context, onLoaded: onLoaded) {
        when (item.type) {
            "op" -> openLoader(context, onLoaded)
            "int" -> interstitialLoader(context, onLoaded)
            else -> onLoaded.invoke(false, "unknown type")
        }
    }

    fun showAd(activity: Activity, onClose: () -> Unit = {}) {
        val admobCallback by lazy {
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() = onAdClose(activity, onClose)
                override fun onAdFailedToShowFullScreenContent(e: AdError) = run {
                    onAdClose(activity, onClose)
                    "${adLoc.placeName} ${item.type} - ${item.id} show failed:${e.message}".logcat()
                }

                override fun onAdClicked() = AdInstance.addClick()
                override fun onAdShowedFullScreenContent() = run {
                    AdInstance.addShow()
                    "${adLoc.placeName} ${item.type} - ${item.id} show success".logcat()
                }
            }
        }

        when (val admobAd = ad) {
            is InterstitialAd -> {
                admobAd.run {
                    fullScreenContentCallback = admobCallback
                    show(activity)
                }
            }
            is AppOpenAd -> {
                admobAd.run {
                    fullScreenContentCallback = admobCallback
                    show(activity)
                }
            }
            else -> onAdClose(activity, onClose)
        }
    }

    private fun onAdClose(activity: Activity, close: () -> Unit = {}) {
        val life = activity as? LifeActivity<*>
        if (null != life) {
            life.lifecycleScope.launch {
                while (!life.resumed) delay(300L)
                close.invoke()
            }
        } else close.invoke()
    }

    @Suppress("DEPRECATION")
    private fun openLoader(context: Context, onLoaded: onLoaded) {
        "${adLoc.placeName} ${item.type} - ${item.id} start load ad".logcat()
        AppOpenAd.load(context, item.id, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdFailedToLoad(e: LoadAdError) = onLoaded.invoke(false, e.message)
            override fun onAdLoaded(openAd: AppOpenAd) = kotlin.run {
                ad = openAd
                openAd.setOnPaidEventListener { onAdRevenue(it, openAd.responseInfo) }
                onLoaded.invoke(true, "")
            }
        })
    }

    private fun interstitialLoader(context: Context, onLoaded: onLoaded) {
        "${adLoc.placeName} ${item.type} - ${item.id} start load ad".logcat()
        InterstitialAd.load(context, item.id, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(e: LoadAdError) = onLoaded.invoke(false, e.message)
            override fun onAdLoaded(interstitialAd: InterstitialAd) = kotlin.run {
                ad = interstitialAd
                interstitialAd.setOnPaidEventListener { onAdRevenue(it, interstitialAd.responseInfo) }
                onLoaded.invoke(true, "")
            }
        })
    }

}