package com.health.pressure.basic.http

import android.os.Build
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.health.pressure.BuildConfig
import com.health.pressure.ext.*
import com.health.pressure.mApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import java.util.*

open class BaseHttp {

    private val baseUrl = "https://test-triple.bloodpressurepro.net/helmsman/phillip/credo"
    private val httpScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) }
    var gaidStr = gaid
    var adTrackEnable = adTracker
    var referrerDataStr = referrerData

    fun startGetter() {
        startReferrerGetter()
        startGoogleAdsGetter()
    }

    fun buildCommonBody(): JSONObject {
        val obj = JSONObject()
        obj.put("boycott", JSONObject().apply {
            put("kilgore", Build.VERSION.RELEASE ?: "")
            put("cambric", netOperator)
            put("aldermen", Build.MODEL ?: "")
            put("wince", installId)
        })
        obj.put("usual", JSONObject().apply {
            put("wiremen", UUID.randomUUID().toString())
            put("prosody", UUID.randomUUID().toString())
            put("beady", gaidStr)
            put("deceive", "chariot")
            put("faro", BuildConfig.VERSION_NAME)
            put("billet", timeZone)
            put("startle", Locale.getDefault().toString())
        })
        obj.put("pbs", JSONObject().apply {
            put("ablate", Build.BRAND ?: "")
            put("esprit", Locale.getDefault().country)
            put("cassock", androidId)
            put("testify", mApp.packageName)
            put("balm", System.currentTimeMillis())
        })
        obj.put("gustav", JSONObject().apply {
            put("opacity", Build.MANUFACTURER ?: "")
        })
        return obj
    }

    private fun startGoogleAdsGetter() {
        httpScope.launch {
            if (gaidStr.isNotBlank()) return@launch
            createFlow(500, 10000L)
                .filter { gaidStr.isBlank() }
                .onEach { "startGoogleAdsGetter".logcat("HttpLog") }
                .collect {
                    runCatching {
                        val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mApp)
                        gaidStr = adInfo.id ?: ""
                        adTrackEnable = adInfo.isLimitAdTrackingEnabled
                        gaid = gaidStr
                        adTracker = adTrackEnable
                    }
                }
        }
    }

    private fun startReferrerGetter() {
        httpScope.launch {
            if (referrerDataStr.isNotBlank()) return@launch
            createFlow(1000, 10000L)
                .filter { referrerDataStr.isBlank() }
                .onEach { "startReferrerGetter".logcat("HttpLog") }
                .collect {
                    runCatching {
                        val referrerClient = InstallReferrerClient.newBuilder(mApp).build()
                        referrerClient.startConnection(object : InstallReferrerStateListener {
                            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                                runCatching {
                                    when (responseCode) {
                                        InstallReferrerClient.InstallReferrerResponse.OK -> {
                                            referrerDataStr = referrerClient.installReferrer?.installReferrer ?: ""
                                            if (referrerDataStr.isNotBlank()) referrerData = referrerDataStr
                                        }
                                        else -> Unit
                                    }
                                    referrerClient.endConnection()
                                }
                            }

                            override fun onInstallReferrerServiceDisconnected() = Unit
                        })
                    }
                }
        }
    }

}