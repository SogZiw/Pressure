package com.health.pressure.ext

import com.tencent.mmkv.MMKV

private val mmkv by lazy { MMKV.defaultMMKV() }

var firstLaunch: Boolean
    get() = mmkv.decodeBool("firstLaunch", true)
    set(value) {
        mmkv.encode("firstLaunch", value)
    }

