package cz.example.stations.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class DeviceEntity (
    @PrimaryKey val id: String,
    val stationId: String,
    val type: String,
    val name: String,
    val status: String,
    val lastSeen: String?
)
