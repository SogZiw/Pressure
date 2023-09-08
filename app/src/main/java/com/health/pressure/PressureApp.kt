package com.health.pressure

import android.app.Application
import com.tencent.mmkv.MMKV

class PressureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mApp = this
        MMKV.initialize(this)
    }
}