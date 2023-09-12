package com.health.pressure.ext

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.health.pressure.mApp

val Int.stringValue: String
    get() = run {
        try {
            mApp.getString(this)
        } catch (e: Exception) {
            ""
        }
    }

val Int.colorValue: Int
    get() = run {
        try {
            ContextCompat.getColor(mApp, this)
        } catch (_: Exception) {
            Color.GRAY
        }
    }