package cz.example.stations.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity (
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val country: String,
    val deviceCount: Int
)
