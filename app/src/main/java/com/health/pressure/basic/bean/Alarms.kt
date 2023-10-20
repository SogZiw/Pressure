package com.health.pressure.basic.bean

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.ext.*
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AlarmItem(
    var timeFormat: String,
    var isOpen: Boolean = true,
) : Parcelable

@Keep
@Parcelize
data class ClockTimeAmount(
    var time: Long = System.currentTimeMillis(),
    var amounts: Int = 0,
) : Parcelable

data class ClockItem(
    val switch: Boolean,
    val first: Int,
    val max: Int,
    val interval: Int,
    var lastShow: Long = 0L,
)

sealed class ClockType {

    abstract val item: ClockItem?
    abstract val isOverMax: Boolean
    abstract fun addMax()

    open fun updateShowTime() {
        item?.lastShow = System.currentTimeMillis()
    }

    object TimeClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.timeClock
        override val isOverMax: Boolean
            get() {
                val entity = timeConf
                return if (entity.time.isToday) entity.amounts >= (item?.max ?: 0) else false
            }

        override fun addMax() {
            val entity = timeConf
            timeConf = if (entity.time.isToday) {
                entity.amounts++
                entity
            } else ClockTimeAmount(amounts = 1)
        }
    }

    object ScreenClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.screenClock
        override val isOverMax: Boolean
            get() {
                val entity = screenConf
                return if (entity.time.isToday) entity.amounts >= (item?.max ?: 0) else false
            }

        override fun addMax() {
            val entity = screenConf
            screenConf = if (entity.time.isToday) {
                entity.amounts++
                entity
            } else ClockTimeAmount(amounts = 1)
        }
    }

    object CharClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.charClock
        override val isOverMax: Boolean
            get() {
                val entity = charConf
                return if (entity.time.isToday) entity.amounts >= (item?.max ?: 0) else false
            }

        override fun addMax() {
            val entity = charConf
            charConf = if (entity.time.isToday) {
                entity.amounts++
                entity
            } else ClockTimeAmount(amounts = 1)
        }
    }

    object UniClock : ClockType() {
        override val item: ClockItem? get() = ClockManager.uniClock
        override val isOverMax: Boolean
            get() {
                val entity = uniConf
                return if (entity.time.isToday) entity.amounts >= (item?.max ?: 0) else false
            }

        override fun addMax() {
            val entity = uniConf
            uniConf = if (entity.time.isToday) {
                entity.amounts++
                entity
            } else ClockTimeAmount(amounts = 1)
        }
    }

}