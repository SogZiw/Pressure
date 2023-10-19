package com.health.pressure.ext

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.health.pressure.basic.bean.AlarmItem
import com.health.pressure.basic.bean.LocalState
import com.health.pressure.mApp
import com.tencent.mmkv.MMKV
import java.util.*

private val mmkv by lazy { MMKV.defaultMMKV() }

private const val FIRST_LAUNCH = "firstLaunch"
private const val FIRST_GUIDE = "firstGuide"
private const val SHOW_RATE = "showRate"
private const val AD_SHOW_TIME = "adShowTime"
private const val RATE_SHOW_TIME = "rateShowTime"
private const val AD_CLICK_TIME = "adClickTime"
private const val AD_SHOW_COUNT = "adShowCount"
private const val AD_CLICK_COUNT = "adClickCount"
private const val DEFAULT_LOCAL_LANG = "default_local_lang"
private const val ALARM_INFO = "alarmInfo"
private const val INSTALL_ID = "install_id"

var firstGuide: Boolean
    get() = mmkv.decodeBool(FIRST_GUIDE, true)
    set(value) {
        mmkv.encode(FIRST_GUIDE, value)
    }

var firstLaunch: Boolean
    get() = mmkv.decodeBool(FIRST_LAUNCH, true)
    set(value) {
        mmkv.encode(FIRST_LAUNCH, value)
    }

var showRate: Boolean
    get() = mmkv.decodeBool(SHOW_RATE, true)
    set(value) {
        mmkv.encode(SHOW_RATE, value)
    }

var rateShowTime: Long
    get() = mmkv.decodeLong(RATE_SHOW_TIME, 0L)
    set(value) {
        mmkv.encode(RATE_SHOW_TIME, value)
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

var defLang: String
    get() = mmkv.decodeString(DEFAULT_LOCAL_LANG, null) ?: LocalState.English.languageCode
    set(value) {
        mmkv.encode(DEFAULT_LOCAL_LANG, value)
    }

var alarmInfo: MutableList<AlarmItem>
    get() {
        val str = mmkv.decodeString(ALARM_INFO, null)
        if (str.isNullOrBlank()) return mutableListOf()
        val type = object : TypeToken<List<AlarmItem>>() {}.type
        return Gson().fromJson<List<AlarmItem>>(str, type).toMutableList()
    }
    set(value) {
        mmkv.encode(ALARM_INFO, Gson().toJson(value))
    }

var referrerData: String
    get() = mmkv.decodeString("referrerData", null) ?: ""
    set(value) {
        mmkv.encode("referrerData", value)
    }

var gaid: String
    get() = mmkv.decodeString("gaid", null) ?: ""
    set(value) {
        mmkv.encode("gaid", value)
    }

var adTracker: Boolean
    get() = mmkv.decodeBool("adTracker", false)
    set(value) {
        mmkv.encode("adTracker", value)
    }

val installId: String
    get() {
        var str = mmkv.decodeString(INSTALL_ID, null) ?: ""
        if (str.isBlank()) str = androidId.ifBlank { UUID.randomUUID().toString().replace("-", "") }
        mmkv.encode(INSTALL_ID, str)
        return str
    }

val androidId: String
    @SuppressLint("HardwareIds")
    get() {
        val id = Settings.Secure.getString(mApp.contentResolver, Settings.Secure.ANDROID_ID)
        return if ("9774d56d682e549c" == id) "" else id ?: ""
    }

val timeZone: Int get() = TimeZone.getDefault().rawOffset / 3600000

val netOperator: String get() = (mApp.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager)?.networkOperator ?: ""
