package com.health.pressure.basic.http

import android.os.Build
import android.webkit.WebSettings
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.health.pressure.BuildConfig
import com.health.pressure.ext.*
import com.health.pressure.mApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*

open class BaseHttp {

    private val client by lazy { OkHttpClient.Builder().build() }
    private val baseUrl = "https://test-triple.bloodpressurepro.net/helmsman/phillip/credo"
    val httpScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) }
    private var adTrackEnable = adTracker
    private var gaidStr = gaid
    var referrerDataStr = referrerData

    fun startGetter() {
        startReferrerGetter()
        startGoogleAdsGetter()
    }

    fun request(obj: JSONObject, tag: String): Boolean {
        fun buildCommonRequest(obj: JSONObject): Request {
            return Request.Builder()
                .addHeader("esprit", Locale.getDefault().country ?: "")
                .post(obj.toString().toRequestBody("application/json".toMediaTypeOrNull()))
                .url("$baseUrl?${URLEncoder.encode(androidId, "utf-8")}&ablate=${URLEncoder.encode(Build.BRAND ?: "", "utf-8")}")
                .build()
        }

        val response = client.newCall(buildCommonRequest(obj)).execute()
        runCatching {
            return if (200 == response.code) {
                "success -- $tag -- ${response.body?.string()}".logcat("HttpLog")
                true
            } else false
        }
        return false
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
                                            install(referrerClient.installReferrer)
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

    private fun install(details: ReferrerDetails?) {
        httpScope.launch {
            val jsonObj = buildCommonBody()
            jsonObj.put("pivotal", JSONObject().apply {
                put("leper", "build/${Build.ID}")
                put("seat", details?.installReferrer ?: "")
                put("lummox", details?.installVersion ?: "")
                put("wise", WebSettings.getDefaultUserAgent(mApp) ?: "")
                put("gleason", if (adTrackEnable) "parmesan" else "concave")
                put("scabious", details?.referrerClickTimestampSeconds ?: 0L)
                put("incense", details?.installBeginTimestampSeconds ?: 0L)
                put("hadnt", details?.referrerClickTimestampServerSeconds ?: 0L)
                put("bacillus", details?.installBeginTimestampServerSeconds ?: 0L)
                put("tasty", details?.googlePlayInstantParam ?: false)
                put("down", mApp.firstInstallTime)
                put("grebe", mApp.lastUpdateTime)
            })
            request(jsonObj, "i-n-s-t-a-l-l".replace("-", ""))
        }
    }

}