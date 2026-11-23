package cz.example.stations

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://test-ucs-mock-api.vercel.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: StationsApi = retrofit.create(StationsApi::class.java)
}
