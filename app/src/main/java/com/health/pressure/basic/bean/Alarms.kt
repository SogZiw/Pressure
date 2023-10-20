package com.health.pressure.basic.bean

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import com.health.pressure.basic.clock.ClockManager
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AlarmItem(
    var timeFormat: String,
    var isOpen: Boolean = true,
) : Parcelable


data class ClockItem(
    val switch: Boolean,
    val first: Int,
    val max: Int,
    val interval: Int,
)

sealed class ClockType {

    abstract val item: ClockItem?

    object TimeClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.timeClock
    }

    object ScreenClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.screenClock
    }

    object CharClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.charClock
    }

    object UniClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.uniClock
    }

}