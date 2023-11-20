package com.health.pressure.basic.clock

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.health.pressure.R
import com.health.pressure.activity.SplashActivity
import com.health.pressure.ext.stringValue
import com.health.pressure.mApp
import kotlin.random.Random

class ForeClockService : Service() {

    private val channelId = "BloodPressureTool"
    private val channelName = "BloodPressureTool"

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForeground()
    }

    private fun startForeground() {
        runCatching {
            buildChannel()
            val notification = buildNotification()
            startForeground(10000, notification)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(mApp, channelId)
            .setSmallIcon(R.drawable.ic_small_logo)
            .setContentTitle(R.string.app_name.stringValue)
            .setContentText("Data synchronizing...")
            .setOngoing(true)
            .setContentIntent(PendingIntent.getActivity(mApp,
                Random.nextInt(10000, 30000),
                Intent(mApp, SplashActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE))
            .setGroupSummary(false)
            .setGroup("BloodPro")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }


    private fun buildChannel() {
        NotificationManagerCompat.from(mApp).createNotificationChannel(NotificationChannelCompat.Builder(channelId, NotificationManager.IMPORTANCE_DEFAULT)
            .setName(channelName)
            .setShowBadge(true)
            .setVibrationEnabled(true)
            .setLightsEnabled(true)
            .build())
    }

}