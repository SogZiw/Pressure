package com.health.pressure.basic.http

import kotlinx.coroutines.launch
import org.json.JSONObject

object EventPost : BaseHttp() {

    fun session() {
        httpScope.launch {
            val jsonObj = buildCommonBody().apply {
                put("bus", JSONObject())
            }
            request(jsonObj, "s-e-s-s-i-o-n".replace("-", ""))
        }
    }

    fun impression(block: (JSONObject) -> Unit) {
        httpScope.launch {
            val jsonObj = buildCommonBody().apply {
                put("ghana", "simla")
            }
            block.invoke(jsonObj)
            request(jsonObj, "a-d-I-m-p-r-e-s-s-i-o-n".replace("-", ""))
        }
    }

}