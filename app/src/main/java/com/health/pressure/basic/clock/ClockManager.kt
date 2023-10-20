package com.health.pressure.basic.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import com.health.pressure.R
import com.health.pressure.basic.AppLife
import com.health.pressure.basic.bean.ClockItem
import com.health.pressure.basic.bean.ClockType
import com.health.pressure.basic.http.EventPost
import com.health.pressure.ext.createFlow
import com.health.pressure.ext.firstInstallTime
import com.health.pressure.ext.isCkEnable
import com.health.pressure.ext.stringValue
import com.health.pressure.mApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn

object ClockManager {

    private val clockScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) }
    var toggle: Boolean = false
    var referrerCode: Int = 2
    var timeClock: ClockItem? = null
    var screenClock: ClockItem? = null
    var charClock: ClockItem? = null
    var uniClock: ClockItem? = null

    private val normalReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_USER_PRESENT -> {
                        clockScope.launch {
                            delay(1000L)
                            ClockType.ScreenClock.showIfCan()
                        }
                    }
                    Intent.ACTION_POWER_CONNECTED -> {
                        ClockType.CharClock.showIfCan()
                    }
                    else -> Unit
                }
            }
        }
    }

    private val pkgReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_PACKAGE_REMOVED -> {
                        clockScope.launch {
                            delay(1000L)
                            ClockType.UniClock.showIfCan()
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    fun startClock(context: Context) {
        register(context)
        startTimer()
    }

    private fun register(context: Context) {
        context.registerReceiver(normalReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_USER_PRESENT)
            addAction(Intent.ACTION_POWER_CONNECTED)
        })
        context.registerReceiver(pkgReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        })
    }

    private fun startTimer() {
        clockScope.launch {
            createFlow(30000L, 60000L)
                .flowOn(Dispatchers.IO)
                .collect {
                    ClockType.TimeClock.showIfCan()
                }
        }
    }

    private fun ClockType.showIfCan() {
        if (canAlarm()) ClockUpper.show(this)
    }

    private fun ClockType.canAlarm(): Boolean {
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

    private fun judgeState(): Boolean {
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
    val recordSet by lazy {
        hashSetOf(
            R.string.record_1.stringValue,
            R.string.record_2.stringValue,
            R.string.record_3.stringValue,
            R.string.record_4.stringValue,
            R.string.record_5.stringValue,
            R.string.record_6.stringValue,
            R.string.record_7.stringValue
        )
    }
    val articleSet by lazy {
        hashSetOf(
            R.string.article_1.stringValue,
            R.string.article_2.stringValue,
            R.string.article_3.stringValue,
            R.string.article_4.stringValue,
            R.string.article_5.stringValue,
            R.string.article_6.stringValue,
        )
    }


    private fun Context.isInteractive(): Boolean = (getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive

}