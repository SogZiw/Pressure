import androidx.lifecycle.asLiveData
import androidx.room.Room
import com.health.pressure.dao.Pressure
import com.health.pressure.dao.PressureDatabase
import com.health.pressure.mApp

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

    fun getAllPressures() = manageDao.getAllPressures().asLiveData()

    fun sameOrNull(time: String) = manageDao.sameOrNull(time).asLiveData()

    fun getPressures(start: Long, end: Long) = manageDao.getPressures(start, end).asLiveData()

}