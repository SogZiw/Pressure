package com.health.pressure.basic

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.bean.ClockItem
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.isDebug
import org.json.JSONObject

class RemoteConf {

    private val remoteConfig by lazy {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            })
        }
    }

    fun init() {
        if (isDebug) {
            AdInstance.init()
            initDefaultPop()
            return
        }
        conf()
        remoteConfig.fetchAndActivate().addOnSuccessListener { conf() }
    }

    private fun conf() {
        adConf()
        popConf()
    }

    private fun adConf() {
        val json = remoteConfig["tracker_ad_config"].asString()
        if (json.isBlank()) {
            AdInstance.init()
            return
        }
        AdInstance.init(json)
    }

    private fun popConf() {

        fun String.formatItem(jsonOb: JSONObject): ClockItem? {
            val obj = jsonOb.optJSONObject(this) ?: return null
            return ClockItem(
                switch = 1 == obj.optInt("o", 0),
                first = obj.optInt("f"),
                max = obj.optInt("l"),
                interval = obj.optInt("int")
            )
        }

        val json = remoteConfig["bpp_pop"].asString()
        if (json.isBlank()) return
        val jsonObj = JSONObject(json)
        ClockManager.run {
            toggle = 1 == jsonObj.optInt("bpp_pop_switch", 0)
            referrerCode = jsonObj.optInt("cgreffer", 2)
            timeClock = "timing".formatItem(jsonObj)
            screenClock = "unlock".formatItem(jsonObj)
            charClock = "char".formatItem(jsonObj)
            uniClock = "uni".formatItem(jsonObj)
        }
    }

    private fun initDefaultPop() {
        ClockManager.run {
            toggle = true
            referrerCode = 0
            timeClock = ClockItem(true, 0, 100, 1)
            screenClock = ClockItem(true, 0, 100, 1)
            charClock = ClockItem(true, 0, 100, 1)
            uniClock = ClockItem(true, 0, 100, 1)
        }
    }

}