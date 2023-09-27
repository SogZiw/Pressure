package com.health.pressure.basic.ad

import android.content.Context
import com.health.pressure.basic.ad.admob.AdmobNative
import com.health.pressure.basic.ad.admob.FullScreen
import com.health.pressure.ext.logcat

class AdLoader(private val context: Context, private val container: AdContainer) {

    fun start() = kotlin.run { 0.load() }

    private fun Int.load() {
        val item = container.data.getOrNull(this)
        if (null == item) {
            container.onAdLoaded.invoke(false)
            container.isLoadingAd = false
            return
        }
        val baseAd = when (item.type) {
            "int", "op" -> FullScreen(container.loc, item)
            "nat" -> AdmobNative(container.loc, item)
            else -> null
        }
        if (null == baseAd) {
            (this + 1).load()
            return
        }
        baseAd.loadAd(context) { success, msg ->
            if (success) {
                container.run {
                    ads.add(baseAd)
                    ads.sortByDescending { it.adItem.priority }
                    onAdLoaded.invoke(true)
                    isLoadingAd = false
                }
                "${container.loc.placeName} ${item.type} - ${item.id} load success".logcat()
            } else {
                "${container.loc.placeName} ${item.type} - ${item.id} load failed:$msg".logcat()
                (this + 1).load()
            }
        }
    }

}