package cz.example.stations.data.model

data class Device (
    val id: String,
    val type: String,
    val name: String,
    val status: String,
    val lastSeen: String?
)
