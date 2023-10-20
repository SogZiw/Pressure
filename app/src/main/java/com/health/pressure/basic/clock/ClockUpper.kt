package com.health.pressure.basic.clock

import android.app.NotificationManager
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.health.pressure.basic.bean.ClockType
import com.health.pressure.mApp

object ClockUpper {

    private const val channelId = "BloodPressurePro"
    private const val channelName = "BloodPressureClock"

    fun show(clockType: ClockType) {
        buildChannel()

    }

    private fun buildChannel() {
        NotificationManagerCompat.from(mApp).createNotificationChannel(NotificationChannelCompat.Builder(channelId, NotificationManager.IMPORTANCE_HIGH)
            .setName(channelName)
            .setShowBadge(true)
            .setVibrationEnabled(true)
            .setLightsEnabled(true)
            .build())
    }



}