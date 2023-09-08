package com.health.pressure.ext

import android.content.Context
import android.graphics.Color
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

fun Context.autoDensity() {
    resources.displayMetrics.run {
        density = heightPixels / 760F
        scaledDensity = density
        densityDpi = (density * 160).toInt()
    }
}

fun Window.fullScreenMode(isLight: Boolean = true) {
    WindowCompat.getInsetsController(this, decorView).run {
        isAppearanceLightStatusBars = isLight
        isAppearanceLightNavigationBars = isLight
    }
    statusBarColor = Color.TRANSPARENT
    navigationBarColor = Color.TRANSPARENT
    WindowCompat.setDecorFitsSystemWindows(this, false)
    ViewCompat.setOnApplyWindowInsetsListener(decorView) { _, insets ->
        decorView.setPadding(0, 0, 0, insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom)
        insets
    }
}

