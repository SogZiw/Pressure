package com.health.pressure

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.google.android.gms.ads.MobileAds
import com.health.pressure.basic.AppLife
import com.health.pressure.basic.RemoteConf
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.basic.http.EventPost
import com.health.pressure.ext.installId
import com.health.pressure.ext.isMain
import com.health.pressure.ext.startForegroundService
import com.tencent.mmkv.MMKV

class PressureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mApp = this
        //RecordLib(this).start()
        if (!isMain()) return
        MMKV.initialize(this)
        startForegroundService()
        MobileAds.initialize(this)
        RemoteConf().init()
        registerActivityLifecycleCallbacks(AppLife)
        initAdjust()
        EventPost.startGetter()
        ClockManager.startClock(this)
    }

    private fun initAdjust() {
        Adjust.addSessionCallbackParameter("customer_user_id", installId)
        val environment = if (isDebug) AdjustConfig.ENVIRONMENT_SANDBOX else AdjustConfig.ENVIRONMENT_PRODUCTION
        Adjust.onCreate(AdjustConfig(this, "ih2pm2dr3k74", environment).apply { setDelayStart(5.0) })
    }
}