package com.health.pressure.basic.ad

import android.app.Activity
import android.content.Context
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.basic.ad.admob.FullScreenAd
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
        if (ads.isNotEmpty()) return
        if (isLoadingAd) return
        isLoadingAd = true
        AdLoader(context, this).start()
    }

    fun showFullScreenAd(activity: Activity, onClose: () -> Unit) {
        if (ads.isEmpty()) {
            onClose.invoke()
            return
        }
        val baseAd = ads.removeFirstOrNull()
        if (null == baseAd) {
            onClose.invoke()
            return
        }
        when (baseAd) {
            is FullScreenAd -> {
                baseAd.showAd(activity, onClose)
            }
        }
        onAdLoaded = {}
        loadAd(activity)
    }

}