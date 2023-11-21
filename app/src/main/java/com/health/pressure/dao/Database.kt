package com.health.pressure.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Pressure::class, Sugar::class], version = 2)
abstract class PressureDatabase : RoomDatabase() {
    abstract fun manageDao(): ManageDao

    // 添加数据库迁移
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `sugar_table` " +
                        "(`uid` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "`condition` INTEGER, " +
                        "`value` INTEGER, " +
                        "`record_time` INTEGER, " +
                        "`format_time` TEXT)")
            }
        }
    }


}