package cz.example.stations.data.repository

import cz.example.stations.data.local.AppDatabase
import cz.example.stations.data.local.toEntity
import cz.example.stations.data.local.toModel
import cz.example.stations.data.model.Device
import cz.example.stations.data.model.Station
import cz.example.stations.data.remote.StationsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

class StationRepository(
    private val api: StationsApi,
    private val database: AppDatabase,
) {
    suspend fun getStations() {
        val stations = api.getStations()

        database.stationDao().upsertStations(
            stations.map { it.toEntity() }
        )
    }

    suspend fun getDevices(stationId: String) {
        val devices = api.getDevices(stationId)

        database.deviceDao().upsertDevices(
            devices.map { it.toEntity(stationId) }
        )
    }
    fun observeStations(): Flow<List<Station>> {
        return database.stationDao().getStations().map { entities ->
            entities.map { it.toModel() }
        }
    }
    fun observeDevices(stationId: String): Flow<List<Device>> {
        return database.deviceDao().getDevices(stationId).map { entities ->
            entities.map { it.toModel() }
        }
    }
}