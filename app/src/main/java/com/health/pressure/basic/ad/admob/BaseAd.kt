package com.health.pressure.basic.ad.admob

import android.content.Context
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.ResponseInfo
import com.health.pressure.basic.ad.AdItem
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.onLoaded
import com.health.pressure.basic.http.EventPost

sealed class BaseAd(val loc: AdLocation, val adItem: AdItem, private val loadTime: Long = System.currentTimeMillis()) {

    abstract fun loadAd(context: Context, onLoaded: onLoaded = { _, _ -> })

    open fun destroy() = Unit

    open fun onAdRevenue(adValue: AdValue, responseInfo: ResponseInfo?) {
        EventPost.impression {
            it.put("soviet", adValue.valueMicros)
            it.put("obscure", adValue.currencyCode)
            it.put("install", getAdapterName(responseInfo))
            it.put("medicine", "admob")
            it.put("fungal", adItem.id)
            it.put("dawn", loc.placeName)
            it.put("gizmo", adItem.getTypeFormat())
            it.put("diagonal", adValue.precisionType)
        }
    }

    private fun getAdapterName(responseInfo: ResponseInfo?): String {
        val clzName = responseInfo?.mediationAdapterClassName ?: "admob"
        return when {
            clzName.contains("pangle", true) -> "pangle"
            clzName.contains("facebook", true) -> "facebook"
            else -> "admob"
        }
    }

    val isOverload: Boolean get() = System.currentTimeMillis() - loadTime >= adItem.overload * 1000L
}
