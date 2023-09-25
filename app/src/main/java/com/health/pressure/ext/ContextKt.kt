package com.health.pressure.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.health.pressure.Constants
import com.health.pressure.R
import com.health.pressure.activity.WebviewActivity

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
        decorView.setPadding(0, insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
            0, insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom)
        insets
    }
}

inline fun <reified Cls : Activity> Context.goNextPage(autoFinish: Boolean = false, function: Intent.() -> Unit = {}) {
    startActivity(Intent(this, Cls::class.java).apply(function))
    if (autoFinish) (this as? Activity)?.finish()
}

fun Activity.buildAgreement(): SpannableStringBuilder {
    return SpannableStringBuilder()
        .append(R.string.privacy_policy.stringValue, object : ClickableSpan() {
            override fun onClick(widget: View) {
                goNextPage<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, Constants.PRIVACY_POLICY) }
            }
        }, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        .append(" & ")
        .append(R.string.user_agreement.stringValue, object : ClickableSpan() {
            override fun onClick(widget: View) {
                goNextPage<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, Constants.USER_AGREE) }
            }
        }, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}
