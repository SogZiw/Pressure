package com.health.pressure.ext

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.animation.LinearInterpolator
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

fun Int.toast() = this.stringValue.toast()

fun Long.startValueAnimator(start: Int = 0, end: Int = 100, onUpdateValue: (value: Int) -> Unit = {}, onEnd: () -> Unit = {}): ValueAnimator {
    return ValueAnimator.ofInt(start, end).apply {
        duration = this@startValueAnimator
        interpolator = LinearInterpolator()
        addUpdateListener {
            (it.animatedValue as? Int)?.let { intValue ->
                onUpdateValue.invoke(intValue)
                if (end == intValue) onEnd.invoke()
            }
        }
        start()
    }
}

