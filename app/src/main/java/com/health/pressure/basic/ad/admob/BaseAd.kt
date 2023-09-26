package com.health.pressure.basic.ad.admob

import android.content.Context
import com.google.android.gms.ads.AdValue
import com.health.pressure.basic.ad.AdItem
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.onLoaded

sealed class BaseAd(val loc: AdLocation, val adItem: AdItem, val loadTime: Long = System.currentTimeMillis()) {

    abstract fun loadAd(context: Context, onLoaded: onLoaded = { _, _ -> })

    open fun destroy() = Unit

    open fun onAdRevenue(adValue: AdValue) {

    }
}
