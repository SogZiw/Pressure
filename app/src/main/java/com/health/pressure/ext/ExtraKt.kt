package com.health.pressure.ext

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import java.math.RoundingMode

fun View.corner(dp: Int) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(0, 0, width, height, context.dp2px(dp))
        }
    }
}

fun createFlow(first: Long, interval: Long) = flow {
    delay(first)
    while (true) {
        emit(Unit)
        delay(interval)
    }
}

fun Int.getFormatUnit(withUnit: Boolean = false, withSeparator: Boolean = false): String {
    return if (isHgUnit) "$this${if (withUnit) getUnit(withSeparator) else ""}"
    else "${(this * 0.1333).round2F()}${if (withUnit) getUnit(withSeparator) else ""}"
}

fun Double.getFormatUnit(withUnit: Boolean = false, withSeparator: Boolean = false): String {
    return if (isHgUnit) "$this${if (withUnit) getUnit(withSeparator) else ""}"
    else "${(this * 0.1333).round2F()}${if (withUnit) getUnit(withSeparator) else ""}"
}

fun getUnit(withSeparator: Boolean = false): String {
    return if (isHgUnit) "${if (withSeparator) "/" else ""}mmHg"
    else "${if (withSeparator) "/" else ""}kPa"
}

fun Double.round2F(): Double = BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()