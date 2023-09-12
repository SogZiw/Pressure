package com.health.pressure.dao

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "pressure_table")
data class Pressure(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    @ColumnInfo var sys: Int,
    @ColumnInfo var dia: Int,
    @ColumnInfo var record_time: Long,
    @ColumnInfo var format_time: String,
    @ColumnInfo var pul: Int = 20,
    @ColumnInfo var extra: String? = "",
) : Parcelable {

    val state: PressureState
        get() {
            return if (sys < 90 || dia < 60) PressureState.Hypotension
            else if (sys in 90..119 && dia in 60..79) PressureState.Normal
            else if (sys in 120..129 && dia in 60..79) PressureState.Elevated
            else if (sys in 130..139 || dia in 80..89) PressureState.HypertensionStageFirst
            else if (sys in 140..180 || dia in 90..120) PressureState.HypertensionStageSecond
            else PressureState.Hypertensive
        }

}