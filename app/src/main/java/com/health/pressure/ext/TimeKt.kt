package com.health.pressure.ext

import java.text.SimpleDateFormat
import java.util.*

private const val defaultPattern = "yyyy-MM-dd HH:mm"

fun Long.formatTime(pattern: String = defaultPattern) = SimpleDateFormat(pattern, Locale.getDefault()).format(this)