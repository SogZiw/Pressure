package com.health.pressure.basic.clock

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.health.pressure.R
import com.health.pressure.activity.SplashActivity
import com.health.pressure.basic.bean.ClockType
import com.health.pressure.ext.logcat
import com.health.pressure.ext.stringValue
import com.health.pressure.mApp
import kotlin.random.Random

object ClockUpper {

    private const val channelId = "BloodPressurePro"
    private const val channelName = "BloodPressureClock"
    private val largeRecordImg by lazy { listOf(R.drawable.ic_notice_record_1_large, R.drawable.ic_notice_record_2_large) }
    private val largeArtImg by lazy { listOf(R.drawable.ic_notice_art_1_large, R.drawable.ic_notice_art_2_large) }
    private val tinyRecordImg by lazy { listOf(R.drawable.ic_notice_record_1_tiny, R.drawable.ic_notice_record_2_tiny) }
    private val tinyArtImg by lazy { listOf(R.drawable.ic_notice_art_1_tiny, R.drawable.ic_notice_art_2_tiny) }

    fun show(clockType: ClockType) {
        buildChannel()
        runCatching {
            val jumpType = Random.nextInt(0, 2)
            val click = clickIntent(clockType, jumpType)
            val content = if (0 == jumpType) ClockManager.recordSet.random() else ClockManager.articleSet.random()
            val imgRandom = Random.nextInt(0, 2)
            val largeView = customView(click, content, itemImg(jumpType, true)[imgRandom], jumpType, true)
            val tinyView = customView(click, content, itemImg(jumpType, false)[imgRandom], jumpType, false)

            val builder = NotificationCompat.Builder(mApp, channelId)
                .setSmallIcon(R.drawable.ic_small_logo)
                .setGroupSummary(false)
                .setGroup("BloodPro")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                builder
                    .setCustomContentView(tinyView)
                    .setCustomBigContentView(largeView)
                    .setCustomHeadsUpContentView(tinyView)
            } else {
                builder
                    .setCustomContentView(largeView)
                    .setCustomBigContentView(largeView)
                    .setCustomHeadsUpContentView(largeView)
            }
            NotificationManagerCompat.from(mApp).notify(18852, builder.build())
            clockType.updateShowTime()
            clockType.addMax()
            "${clockType.nameAlias} showed".logcat()
        }
    }

    fun cancel() {
        kotlin.runCatching {
            NotificationManagerCompat.from(mApp).cancel(18852)
        }
    }

    private fun buildChannel() {
        NotificationManagerCompat.from(mApp).createNotificationChannel(NotificationChannelCompat.Builder(channelId, NotificationManager.IMPORTANCE_HIGH)
            .setName(channelName)
            .setShowBadge(true)
            .setVibrationEnabled(true)
            .setLightsEnabled(true)
            .build())
    }

    private fun customView(
        click: PendingIntent,
        content: String,
        imgRes: Int,
        jumpType: Int,
        isLarge: Boolean,
    ): RemoteViews {
        val layoutId = if (isLarge) R.layout.notification_large else R.layout.notification_tiny
        return RemoteViews(mApp.packageName, layoutId).apply {
            setOnClickPendingIntent(R.id.nf, click)
            setImageViewResource(R.id.nfImg, imgRes)
            setTextViewText(R.id.nfContent, content)
            setTextViewText(R.id.nfBtn, if (0 == jumpType) R.string.record_now.stringValue else R.string.read_article.stringValue)
        }
    }

    private fun itemImg(jumpType: Int, isLarge: Boolean) = when (jumpType) {
        0 -> if (isLarge) largeRecordImg else tinyRecordImg
        else -> if (isLarge) largeArtImg else tinyArtImg
    }

    private fun clickIntent(clockType: ClockType, jumpType: Int): PendingIntent {
        val intent = Intent(mApp, SplashActivity::class.java).apply {
            putExtra("ClockType", clockType.nameAlias)
            putExtra("JumpType", jumpType)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        return PendingIntent.getActivity(mApp, Random.nextInt(10000, 20000), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }


}