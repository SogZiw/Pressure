package com.health.pressure.ext

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

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