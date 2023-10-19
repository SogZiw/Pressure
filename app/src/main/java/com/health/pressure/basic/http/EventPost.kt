package com.health.pressure.basic.http

import kotlinx.coroutines.launch
import org.json.JSONObject

object EventPost : BaseHttp() {

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

    fun event(key: String, params: HashMap<String, Any?>) {
        httpScope.launch {
            val jsonObj = buildCommonBody().apply {
                put("ghana", key)
                params.onEach { put("custom_${it.key}", it.value) }
            }
            request(jsonObj, "e-v-e-n-t".replace("-", ""))
        }
    }

}