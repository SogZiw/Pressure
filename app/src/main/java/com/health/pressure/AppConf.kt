package com.health.pressure

import com.health.pressure.dao.Pressure

lateinit var mApp: PressureApp

val wheelData by lazy { (20..300).toMutableList() }

val datas = mutableListOf<Pressure>()


object Constants {
    const val WEBVIEW_URL = "webview_url"
    const val PRIVACY_POLICY = "https://www.baidu.com"
    const val USER_AGREE = "https://www.baidu.com"
}