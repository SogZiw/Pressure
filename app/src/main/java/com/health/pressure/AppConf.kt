package com.health.pressure

lateinit var mApp: PressureApp

val wheelData by lazy {
    val list = mutableListOf<String>()
    for (i in 20..300) {
        list.add(i.toString())
    }
    list
}