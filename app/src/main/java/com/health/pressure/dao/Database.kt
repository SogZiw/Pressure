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
                        "(`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`condition` INTEGER NOT NULL, " +
                        "`value` INTEGER NOT NULL, " +
                        "`record_time` INTEGER NOT NULL, " +
                        "`format_time` TEXT NOT NULL)")
            }
        }
    }


}