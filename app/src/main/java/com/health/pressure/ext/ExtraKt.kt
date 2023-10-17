package com.health.pressure.ext

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

fun View.corner(dp: Int) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(0, 0, width, height, context.dp2px(dp))
        }
    }
}