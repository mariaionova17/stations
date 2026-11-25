package cz.example.stations.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Query("SELECT * from stations")
    fun getStations(): Flow<List<StationEntity>>

    @Upsert
    suspend fun upsertStations(stations: List<StationEntity>)

    @Query("DELETE FROM stations")
    suspend fun deleteAllStations()

}
