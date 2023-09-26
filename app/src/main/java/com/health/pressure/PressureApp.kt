package com.health.pressure

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.health.pressure.basic.RemoteConf
import com.tencent.mmkv.MMKV

class PressureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mApp = this
        MMKV.initialize(this)
        MobileAds.initialize(this)
        RemoteConf().init()
    }
}