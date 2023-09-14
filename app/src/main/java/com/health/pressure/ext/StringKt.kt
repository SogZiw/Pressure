package com.health.pressure.ext

import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.health.pressure.BuildConfig
import com.health.pressure.mApp

fun String.toast() {
    Toast.makeText(mApp, this, Toast.LENGTH_LONG).apply {
        setGravity(Gravity.CENTER, 0, 0)
    }.show()
}

fun String?.logcat() {
    if (BuildConfig.DEBUG.not()) return
    Log.e("Pressure", this ?: "string is null or empty")
}