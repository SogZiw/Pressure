package com.health.pressure.basic.http

import android.os.Bundle
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.health.pressure.mApp
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

object EventPost : BaseHttp() {

    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(mApp) }
    private val facebookLogger by lazy { AppEventsLogger.newLogger(mApp) }

    fun session() {
        httpScope.launch {
            val jsonObj = buildCommonBody().apply { put("bus", JSONObject()) }
            request(jsonObj, "s-e-s-s-i-o-n".replace("-", ""))
        }
    }

    fun impression(block: (JSONObject) -> Unit) {
        httpScope.launch {
            val jsonObj = buildCommonBody().apply { put("ghana", "simla") }
            block.invoke(jsonObj)
            request(jsonObj, "a-d-I-m-p-r-e-s-s-i-o-n".replace("-", ""))
        }
    }

    fun event(key: String, params: HashMap<String, Any?> = hashMapOf()) {
        httpScope.launch {
            val jsonObj = buildCommonBody().apply {
                put("ghana", key)
                params.onEach { put("custom_${it.key}", it.value) }
            }
            request(jsonObj, "e-v-e-n-t".replace("-", ""))
        }
    }

    fun firebaseEvent(key: String, params: HashMap<String, Any?> = hashMapOf()) {
        runCatching {
            if (params.isEmpty()) firebaseAnalytics.logEvent(key, null)
            else firebaseAnalytics.logEvent(key, Bundle().apply {
                params.onEach { putString(it.key, it.value.toString()) }
            })
        }
        event(key, params)
    }

    fun facebookRevenue(value: Double) {
        facebookLogger.logPurchase(value.toBigDecimal(), Currency.getInstance("USD"))
    }

}