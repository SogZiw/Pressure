package com.health.pressure.basic

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.os.Bundle
import com.adjust.sdk.Adjust
import com.google.android.gms.ads.AdActivity
import com.health.pressure.activity.SplashActivity
import com.health.pressure.ext.defLang
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.updateLocalConf
import kotlinx.coroutines.*

object AppLife : ActivityLifecycleCallbacks {

    val activitys = mutableListOf<Activity>()
    private var activityJob: Job? = null
    private var startAmounts = 0
    private var hotStart = false

    fun foreground(): Boolean = startAmounts > 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        synchronized(activitys) { activitys.add(activity) }
    }

    override fun onActivityDestroyed(activity: Activity) {
        synchronized(activitys) { activitys.remove(activity) }
    }

    override fun onActivityStarted(activity: Activity) {
        synchronized(activitys) {
            activityJob?.cancel()
            startAmounts++
            if (hotStart) {
                hotStart = false
                if (activity !is SplashActivity) activity.goNextPage<SplashActivity> { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) }
            }
        }
    }

    override fun onActivityStopped(activity: Activity) {
        synchronized(activitys) {
            activityJob?.cancel()
            if (--startAmounts <= 0) {
                activityJob = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    delay(5000)
                    hotStart = true
                    ArrayList(activitys).onEach {
                        if (it is SplashActivity || it is AdActivity) it.finish()
                    }
                }
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        activity.updateLocalConf(defLang)
        activity.application.updateLocalConf(defLang)
        Adjust.onResume()
    }

    override fun onActivityPaused(activity: Activity) = Adjust.onPause()
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

}