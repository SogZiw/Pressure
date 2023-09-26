package com.health.pressure.ext

import com.tencent.mmkv.MMKV

private val mmkv by lazy { MMKV.defaultMMKV() }

private const val FIRST_LAUNCH = "firstLaunch"
private const val AD_SHOW_TIME = "adShowTime"
private const val AD_CLICK_TIME = "adClickTime"
private const val AD_SHOW_COUNT = "adShowCount"
private const val AD_CLICK_COUNT = "adClickCount"

var firstLaunch: Boolean
    get() = mmkv.decodeBool(FIRST_LAUNCH, true)
    set(value) {
        mmkv.encode(FIRST_LAUNCH, value)
    }

var adShowTime: Long
    get() = mmkv.decodeLong(AD_SHOW_TIME, 0L)
    set(value) {
        mmkv.encode(AD_SHOW_TIME, value)
    }

var adClickTime: Long
    get() = mmkv.decodeLong(AD_CLICK_TIME, 0L)
    set(value) {
        mmkv.encode(AD_CLICK_TIME, value)
    }

var adShowCount: Int
    get() = mmkv.decodeInt(AD_SHOW_COUNT, 0)
    set(value) {
        mmkv.encode(AD_SHOW_COUNT, value)
    }

var adClickCount: Int
    get() = mmkv.decodeInt(AD_CLICK_COUNT, 0)
    set(value) {
        mmkv.encode(AD_CLICK_COUNT, value)
    }