import androidx.annotation.WorkerThread
import androidx.room.Room
import com.health.pressure.dao.Pressure
import com.health.pressure.dao.PressureDatabase
import com.health.pressure.mApp

object DataManager {

    private val database by lazy { Room.databaseBuilder(mApp, PressureDatabase::class.java, "pressure_database").allowMainThreadQueries().build() }
    private val manageDao by lazy { database.manageDao() }

    @WorkerThread
    fun insertData(vararg data: Pressure) {
        manageDao.insertData(*data)
    }

    @WorkerThread
    fun updateData(vararg data: Pressure) {
        manageDao.updateData(*data)
    }

    @WorkerThread
    fun deleteData(vararg data: Pressure) {
        manageDao.deleteData(*data)
    }

    @WorkerThread
    fun getAllPressures() = manageDao.getAllPressures()

    @WorkerThread
    fun sameOrNull(time: String): Pressure? {
        return manageDao.sameOrNull(time)
    }

    @WorkerThread
    fun getPressures(start: Long, end: Long): List<Pressure> {
        return manageDao.getPressures(start, end)
    }
}