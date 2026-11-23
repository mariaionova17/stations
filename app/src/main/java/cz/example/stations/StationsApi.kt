package cz.example.stations

import retrofit2.http.GET

interface StationsApi {
    @GET("api/v1/stations")
    suspend fun getStations(): List<Station>
}