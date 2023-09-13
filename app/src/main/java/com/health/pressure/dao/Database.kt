package com.health.pressure.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Pressure::class], version = 1)
abstract class PressureDatabase : RoomDatabase() {
    abstract fun manageDao(): ManageDao
}