package com.health.pressure.basic.clock

import android.content.Context
import android.os.PowerManager
import com.health.pressure.basic.AppLife
import com.health.pressure.basic.bean.ClockItem
import com.health.pressure.basic.bean.ClockType
import com.health.pressure.basic.http.EventPost
import com.health.pressure.ext.firstInstallTime
import com.health.pressure.ext.isCkEnable
import com.health.pressure.mApp

object ClockManager {

    var toggle: Boolean = false
    var referrerCode: Int = 2
    var timeClock: ClockItem? = null
    var screenClock: ClockItem? = null
    var charClock: ClockItem? = null
    var uniClock: ClockItem? = null

    fun ClockType.canAlarm(): Boolean {
        if (AppLife.foreground()) return false
        if (mApp.isInteractive().not()) return false
        if (toggle.not()) return false
        if (judgeState().not()) return false
        if (null == item) return false
        if (System.currentTimeMillis() - mApp.firstInstallTime < (item?.first ?: 0) * 60000L) return false
        if (System.currentTimeMillis() - (item?.lastShow ?: 0L) < (item?.interval ?: 0) * 60000L) return false
        if (isOverMax) return false
        return true
    }

    fun judgeState(): Boolean {
        if (isCkEnable.not()) return false
        return when (referrerCode) {
            0 -> true
            1 -> false
            2 -> bySet.any { EventPost.referrerDataStr.contains(it, true) }
            3 -> fbSet.any { EventPost.referrerDataStr.contains(it, true) }
            else -> false
        }
    }

    private val fbSet by lazy { hashSetOf("fb4a", "facebook") }
    private val bySet by lazy { hashSetOf("bytedance", "%7B%22", "fb4a", "facebook", "gclid", "not%20set", "youtubeads") }


    private fun Context.isInteractive(): Boolean = (getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive

}