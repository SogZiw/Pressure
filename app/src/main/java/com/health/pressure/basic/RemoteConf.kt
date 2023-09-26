package com.health.pressure.basic

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.health.pressure.basic.ad.AdManager
import com.health.pressure.data.AdItem
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
        conf()
        remoteConfig.fetchAndActivate().addOnSuccessListener { conf() }
    }

    private fun conf() {
        adConf()
    }

    private fun adConf() {

        fun formatItem(obj: JSONObject, key: String): MutableList<AdItem> {
            try {
                val itemArr = obj.optJSONArray(key) ?: return mutableListOf()
                val itemList = mutableListOf<AdItem>()
                for (i in 0 until itemArr.length()) {
                    val itemObj = itemArr.getJSONObject(i)
                    val item = AdItem(id = itemObj.optString("lost"),
                        platform = itemObj.optString("dream"),
                        type = itemObj.optString("todo"),
                        priority = itemObj.optInt("ashi"),
                        overload = itemObj.optInt("ppaa")
                    )
                    itemList.add(item)
                }
                itemList.sortByDescending { it.priority }
                return itemList
            } catch (_: Exception) {
                return mutableListOf()
            }
        }

        val json = remoteConfig["tracker_ad_config"].asString()
        AdManager.showMax = JSONObject(json).optInt("hskkhs")
        AdManager.clickMax = JSONObject(json).optInt("jakh")


    }

}