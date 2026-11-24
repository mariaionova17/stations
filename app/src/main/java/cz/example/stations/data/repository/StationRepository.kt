package cz.example.stations.data.repository

import cz.example.stations.data.model.Device
import cz.example.stations.data.model.Station
import cz.example.stations.data.remote.RetrofitClient
import cz.example.stations.data.remote.StationsApi

class StationRepository(
        private val api: StationsApi = RetrofitClient.api
) {
    suspend fun getStations(): List<Station> {
        return api.getStations()
    }

    suspend fun getDevices(stationId: String): List<Device> {
        return api.getDevices(stationId)
    }
}