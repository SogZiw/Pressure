package com.health.pressure.dao

import androidx.lifecycle.asLiveData
import androidx.room.Room
import com.health.pressure.mApp
import kotlinx.coroutines.flow.distinctUntilChanged

object DataManager {

    private val database by lazy { Room.databaseBuilder(mApp, PressureDatabase::class.java, "pressure_database").allowMainThreadQueries().build() }

    private val manageDao by lazy { database.manageDao() }

    fun insertData(vararg data: Pressure) {
        manageDao.insertData(*data)
    }

    fun updateData(vararg data: Pressure) {
        manageDao.updateData(*data)
    }

    fun deleteData(vararg data: Pressure) {
        manageDao.deleteData(*data)
    }

    fun getAllPressures() = manageDao.getAllPressures().distinctUntilChanged().asLiveData()

    fun getAllPressuresFlow() = manageDao.getAllPressures().distinctUntilChanged()

    fun sameOrNull(time: String) = manageDao.sameOrNull(time)

    fun getPressures(start: Long, end: Long) = manageDao.getPressures(start, end)

}