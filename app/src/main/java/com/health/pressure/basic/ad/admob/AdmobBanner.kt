package com.health.pressure.basic.ad.admob

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdItem
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.onLoaded
import com.health.pressure.ext.logcat
import com.health.pressure.ext.screenWidth
import com.health.pressure.mApp

class AdmobBanner(val adLoc: AdLocation, val item: AdItem) : BaseAd(adLoc, item) {

    private var banner: AdView? = null
    private val adRequest get() = AdRequest.Builder().build()

    override fun loadAd(context: Context, onLoaded: onLoaded) {
        "${adLoc.placeName} ${item.type} - ${item.id} start load ad".logcat()
        val adView = AdView(mApp).apply {
            setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, context.screenWidth.toInt()))
            adUnitId = adItem.id
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    banner = this@apply
                    this@apply.setOnPaidEventListener { onAdRevenue(it, this@apply.responseInfo) }
                    onLoaded.invoke(true, "")
                }

                override fun onAdFailedToLoad(e: LoadAdError) = onLoaded.invoke(false, e.message)
                override fun onAdClicked() = AdInstance.addClick()
            }
        }
        adView.loadAd(adRequest)
    }

    fun show(parent: ViewGroup) {
        if (null == banner) return
        kotlin.runCatching {
            (banner?.parent as? ViewGroup)?.removeAllViews()
            parent.removeAllViews()
            parent.addView(banner)
        }
        AdInstance.addShow()
        "${adLoc.placeName} ${item.type} - ${item.id} show success".logcat()
    }


    override fun destroy() {
        banner?.destroy()
    }


}