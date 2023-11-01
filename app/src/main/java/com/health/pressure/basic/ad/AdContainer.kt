package com.health.pressure.basic.ad

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.admob.AdmobBanner
import com.health.pressure.basic.ad.admob.AdmobNative
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.basic.ad.admob.FullScreen
import com.health.pressure.basic.http.EventPost
import com.health.pressure.mApp

class AdContainer(val loc: AdLocation) {

    val data = mutableListOf<AdItem>()
    val ads = mutableListOf<BaseAd>()
    var onAdLoaded: (Boolean) -> Unit = {}
    var isLoadingAd = false

    fun initData(list: MutableList<AdItem>?) {
        data.run {
            clear()
            addAll(list ?: mutableListOf())
        }
    }

    fun loadAd(context: Context = mApp) {
        if (data.isEmpty()) return
        if (AdInstance.isOverMax()) return
        judgeOverload()
        if (ads.isNotEmpty()) return
        if (isLoadingAd) return
        isLoadingAd = true
        AdLoader(context, this).start()
    }

    private fun judgeOverload() {
        if (ads.isNotEmpty()) {
            val item = ads.firstOrNull() ?: return
            if (item.isOverload) ads.remove(item)
        }
    }

    fun canShowFullScreenAd(activity: LifeActivity<*>): Boolean = ads.isNotEmpty() && activity.resumed

    fun showFullScreenAd(activity: Activity, posId: String = loc.placeName, onClose: () -> Unit) {
        if (ads.isEmpty()) {
            onClose.invoke()
            return
        }
        when (val baseAd = ads.removeFirstOrNull()) {
            is FullScreen -> baseAd.showAd(activity, onClose)
            else -> {
                onClose.invoke()
                return
            }
        }
        onAdLoaded = {}
        loadAd(activity)
        EventPost.firebaseEvent("tk_ad_impression", hashMapOf("ad_pos_id" to posId))
    }

    fun keepLoader(context: Context, onLoad: (Boolean) -> Unit = {}) {
        if (ads.isNotEmpty()) onLoad.invoke(true)
        else {
            onAdLoaded = onLoad
            loadAd(context)
        }
    }

    fun showNativeAd(context: Context, parent: ViewGroup, showSmall: Boolean = false, callback: (BaseAd) -> Unit) {
        if (ads.isEmpty()) return
        when (val baseAd = ads.removeFirstOrNull()) {
            is AdmobNative -> {
                if (showSmall) baseAd.showSmall(context, parent) else baseAd.show(context, parent)
                callback.invoke(baseAd)
            }
            else -> Unit
        }
        onAdLoaded = {}
        loadAd(context)
        EventPost.firebaseEvent("tk_ad_impression", hashMapOf("ad_pos_id" to loc.placeName))
    }

    fun showBanner(context: Context, parent: ViewGroup, callback: (BaseAd) -> Unit) {
        if (ads.isEmpty()) return
        when (val baseAd = ads.removeFirstOrNull()) {
            is AdmobBanner -> {
                baseAd.show(parent)
                callback.invoke(baseAd)
            }
            else -> Unit
        }
        onAdLoaded = {}
        EventPost.firebaseEvent("tk_ad_impression", hashMapOf("ad_pos_id" to loc.placeName))
    }

}