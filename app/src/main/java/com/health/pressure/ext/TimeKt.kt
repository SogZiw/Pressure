package com.health.pressure.ext

import java.text.SimpleDateFormat
import java.util.*

private const val defaultPattern = "yyyy-MM-dd HH:mm"

fun Long.formatTime(pattern: String = defaultPattern): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)

val currentTimeMills: Long get() = System.currentTimeMillis()
val last24HoursTime: Long get() = currentTimeMills - 24 * 60 * 60000L
val last7DaysTime: Long get() = currentTimeMills - 7 * 24 * 60 * 60000L

val Long.isToday: Boolean
    get() {
        val wee = todayStartTime
        return this >= wee && this < wee + 86400000
    }

val todayStartTime: Long
    get() {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

val weekStartTime: Long
    get() {
        return Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.SUNDAY
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

val thisMonthStartTime: Long
    get() {
        return Calendar.getInstance().apply {
            add(Calendar.MONTH, 0)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

val lastMonthStartTime: Long
    get() {
        return Calendar.getInstance().apply {
            add(Calendar.MONTH, -1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

val lastMonthEndTime: Long
    get() {
        return Calendar.getInstance().apply {
            add(Calendar.MONTH, -1)
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis
    }