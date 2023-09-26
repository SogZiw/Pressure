package com.health.pressure.basic.ad

import com.health.pressure.Constants
import com.health.pressure.ext.*
import org.json.JSONObject

object AdInstance {

    var showMax = 0
    var clickMax = 0

    val openAd = AdContainer(AdLocation.OPEN)

    fun init(json: String = Constants.AD_JSON) {

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

        val jsonObj = JSONObject(json)
        showMax = jsonObj.optInt("hskkhs")
        clickMax = jsonObj.optInt("jakh")
        openAd.initData(formatItem(jsonObj, AdLocation.OPEN.placeName))
    }

    fun addShowCount() {
        runCatching {
            if (adShowTime.isToday.not()) {
                adShowTime = System.currentTimeMillis()
                adShowCount = 1
            } else adShowCount++
        }
    }

    fun addClickCount() {
        runCatching {
            if (adClickTime.isToday.not()) {
                adClickTime = System.currentTimeMillis()
                adClickCount = 1
            } else adClickCount++
        }
    }

    fun isOverMax(): Boolean {
        if (0 == showMax) return false
        val overShow = if (adShowTime.isToday) adShowCount >= showMax else false
        val overClick = if (adClickTime.isToday) adClickCount >= clickMax else false
        return overShow || overClick
    }

}