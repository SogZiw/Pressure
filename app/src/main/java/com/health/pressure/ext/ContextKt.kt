package com.health.pressure.ext

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Process
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.health.pressure.Constants
import com.health.pressure.R
import com.health.pressure.activity.WebviewActivity
import com.health.pressure.databinding.DialogRateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

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

fun Application.isMain(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) Application.getProcessName() == packageName else isMainProcess()
}

private fun Application.isMainProcess(): Boolean {
    try {
        val am = getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
        val infos = am.runningAppProcesses
        if (infos.isNullOrEmpty()) return false
        val pid = Process.myPid()
        return infos.any { it.pid == pid && it.processName == packageName }
    } catch (e: Exception) {
        return false
    }
}

fun Context.dp2px(num: Int) = resources.displayMetrics.density * num

inline fun <reified Cls : Activity> Context.goNextPage(autoFinish: Boolean = false, function: Intent.() -> Unit = {}) {
    startActivity(Intent(this, Cls::class.java).apply(function))
    if (autoFinish) (this as? Activity)?.finish()
}

fun <T> Flow<T>.launchIn(scope: CoroutineScope): Job = scope.launch { collect() }

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

fun Context.updateLocalConf(lang: String): Context {
    val config = Configuration(resources.configuration).apply { setLocale(Locale(lang)) }
    resources.updateConfiguration(config, resources.displayMetrics)
    return createConfigurationContext(config)
}

fun Context.rate() {
    runCatching {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
            setPackage("com.android.vending")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}

fun Activity.createRateDialog(onRate: () -> Unit) {
    val binding = DialogRateBinding.inflate(layoutInflater, window.decorView as ViewGroup, false)
    val dialog = AlertDialog.Builder(this)
        .setView(binding.root)
        .setCancelable(true)
        .create()
    binding.btnRate.setOnClickListener {
        dialog.dismiss()
        onRate.invoke()
        rate()
    }
    binding.root.corner(20)
    dialog.window?.decorView?.background = null
    dialog.show()
}

fun Context.createCommonDialog(
    msg: String,
    positive: String = R.string.sure.stringValue,
    negative: String = R.string.cancel.stringValue,
    onPositive: () -> Unit = {},
    onNegative: () -> Unit = {},
) {
    val dialog = AlertDialog.Builder(this)
        .setCancelable(true)
        .setMessage(msg)
        .setPositiveButton(positive) { _, _ ->
            onPositive.invoke()
        }
        .setNegativeButton(negative) { _, _ ->
            onNegative.invoke()
        }
        .create()
    dialog.show()
}