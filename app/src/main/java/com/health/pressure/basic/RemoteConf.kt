package com.health.pressure.basic

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.isDebug

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
            return
        }
        conf()
        remoteConfig.fetchAndActivate().addOnSuccessListener { conf() }
    }

    private fun conf() {
        adConf()
    }

    private fun adConf() {
        val json = remoteConfig["tracker_ad_config"].asString()
        if (json.isBlank()) return
        AdInstance.init(json)
    }

}