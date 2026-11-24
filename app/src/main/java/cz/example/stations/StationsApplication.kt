package cz.example.stations

import android.app.Application
import androidx.room.Room
import cz.example.stations.data.local.AppDatabase
import cz.example.stations.data.remote.RetrofitClient
import cz.example.stations.data.repository.StationRepository

class StationsApplication: Application() {
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "stations.db"
        ).build()
    }

    val repository by lazy {
        StationRepository(RetrofitClient.api, database)
    }
}