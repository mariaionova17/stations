package cz.example.stations.data.model

data class Station(
    val id: String,
    val name: String,
    val address: String,
    val country: String,
    val deviceCount: Int,
)
