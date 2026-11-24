package cz.example.stations.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices WHERE stationId = :stationId")
    fun getDevices(stationId: String): Flow<List<DeviceEntity>>

    @Upsert
    suspend fun upsertDevices(devices: List<DeviceEntity>)
}
