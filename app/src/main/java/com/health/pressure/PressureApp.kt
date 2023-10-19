package com.health.pressure

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.health.pressure.basic.AppLife
import com.health.pressure.basic.RemoteConf
import com.health.pressure.basic.http.EventPost
import com.health.pressure.ext.isMain
import com.tencent.mmkv.MMKV

class PressureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mApp = this
        if (!isMain()) return
        MMKV.initialize(this)
        MobileAds.initialize(this)
        RemoteConf().init()
        registerActivityLifecycleCallbacks(AppLife)
        EventPost.startGetter()
    }
}