package com.health.pressure.ext

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.provider.Settings
import android.telephony.TelephonyManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.health.pressure.basic.bean.AlarmItem
import com.health.pressure.basic.bean.ClockTimeAmount
import com.health.pressure.basic.bean.LocalState
import com.health.pressure.mApp
import com.tencent.mmkv.MMKV
import java.util.*
import kotlin.reflect.KProperty

private val mmkv by lazy { MMKV.defaultMMKV() }

private const val FIRST_LAUNCH = "firstLaunch"
private const val SHOW_RATE = "showRate"
private const val AD_SHOW_TIME = "adShowTime"
private const val RATE_SHOW_TIME = "rateShowTime"
private const val AD_CLICK_TIME = "adClickTime"
private const val AD_SHOW_COUNT = "adShowCount"
private const val AD_CLICK_COUNT = "adClickCount"
private const val DEFAULT_LOCAL_LANG = "default_local_lang"
private const val ALARM_INFO = "alarmInfo"
private const val INSTALL_ID = "install_id"
private const val IS_HG_UNIT = "isHgUnit"
private const val GUIDE_STEP = "guide_step"

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

var timeConf: ClockTimeAmount
    get() = mmkv.decodeParcelable("timeConf", ClockTimeAmount::class.java) ?: ClockTimeAmount()
    set(value) {
        mmkv.encode("timeConf", value)
    }
var screenConf: ClockTimeAmount
    get() = mmkv.decodeParcelable("screenConf", ClockTimeAmount::class.java) ?: ClockTimeAmount()
    set(value) {
        mmkv.encode("screenConf", value)
    }
var charConf: ClockTimeAmount
    get() = mmkv.decodeParcelable("charConf", ClockTimeAmount::class.java) ?: ClockTimeAmount()
    set(value) {
        mmkv.encode("charConf", value)
    }
var uniConf: ClockTimeAmount
    get() = mmkv.decodeParcelable("uniConf", ClockTimeAmount::class.java) ?: ClockTimeAmount()
    set(value) {
        mmkv.encode("uniConf", value)
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

// true is white user
var isCkEnable: Boolean
    get() = mmkv.decodeBool("isCkEnable", false)
    set(value) {
        mmkv.encode("isCkEnable", value)
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

var isHgUnit: Boolean
    get() = mmkv.decodeBool(IS_HG_UNIT, true)
    set(value) {
        mmkv.encode(IS_HG_UNIT, value)
    }

var guideStep: Int
    get() = mmkv.decodeInt(GUIDE_STEP, -1)
    set(value) {
        mmkv.encode(GUIDE_STEP, value)
    }

class stringPref(private val defaultValue: String = "") {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = mmkv.decodeString(property.name, defaultValue) ?: defaultValue
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) = run { mmkv.encode(property.name, value) }
}

class longPref(private val defaultValue: Long = 0L) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long = mmkv.decodeLong(property.name, defaultValue)
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) = run { mmkv.encode(property.name, value) }
}

class doublePref(private val defaultValue: Double = 0.0) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Double = mmkv.decodeDouble(property.name, defaultValue)
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) = run { mmkv.encode(property.name, value) }
}

class intPref(private val defaultValue: Int = 0) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int = mmkv.decodeInt(property.name, defaultValue)
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) = run { mmkv.encode(property.name, value) }
}

class booleanPref(private val defaultValue: Boolean = false) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean = mmkv.decodeBool(property.name, defaultValue)
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) = run { mmkv.encode(property.name, value) }
}

class parcelablePref<T : Parcelable>(private val type: Class<T>, private val defaultValue: T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = mmkv.decodeParcelable(property.name, type) ?: defaultValue
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = run { mmkv.encode(property.name, value) }
}