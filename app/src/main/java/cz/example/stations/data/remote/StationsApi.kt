package cz.example.stations.data.remote

import cz.example.stations.data.model.Device
import cz.example.stations.data.model.Station
import retrofit2.http.GET
import retrofit2.http.Path

interface StationsApi {
    @GET("api/v1/stations")
    suspend fun getStations(): List<Station>

    @GET("api/v1/stations/{stationId}/devices")
    suspend fun getDevices(@Path("stationId") stationId: String): List<Device>
}
