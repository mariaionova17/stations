package cz.example.stations.data.remote

import cz.example.stations.data.model.Station
import retrofit2.http.GET

interface StationsApi {
    @GET("api/v1/stations")
    suspend fun getStations(): List<Station>
}