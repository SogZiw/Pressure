package com.health.pressure.dao

import androidx.annotation.Keep
import androidx.room.*

@Keep
@Dao
interface ManageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg data: Pressure)

    @Update
    fun updateData(vararg data: Pressure)

    @Delete
    fun deleteData(vararg data: Pressure)

    @Query("SELECT * FROM pressure_table")
    fun getAllPressures(): List<Pressure>

    @Query("SELECT * FROM pressure_table WHERE format_time = :time LIMIT 1")
    fun sameOrNull(time: String): Pressure?

    @Query("SELECT * FROM pressure_table WHERE record_time BETWEEN :start AND :end")
    fun getPressures(start: Long, end: Long): List<Pressure>

}