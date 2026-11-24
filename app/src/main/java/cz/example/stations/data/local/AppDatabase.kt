package cz.example.stations.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StationEntity::class, DeviceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun deviceDao(): DeviceDao
}
