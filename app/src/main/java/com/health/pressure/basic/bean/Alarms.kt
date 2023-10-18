package com.health.pressure.basic.bean

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AlarmItem(
    var timeFormat: String,
    var isOpen: Boolean = true,
) : Parcelable