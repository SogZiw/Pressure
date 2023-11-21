package com.health.pressure.dao

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "sugar_table")
data class Sugar(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo var condition: Int,
    @ColumnInfo var value: Int,
    @ColumnInfo var record_time: Long,
    @ColumnInfo var format_time: String,
) : Parcelable