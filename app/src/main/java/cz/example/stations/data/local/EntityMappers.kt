package cz.example.stations.data.local

import cz.example.stations.data.model.Station
import cz.example.stations.data.model.Device

fun Station.toEntity() = StationEntity(
    id = id,
    name = name,
    address = address,
    country = country,
    deviceCount = deviceCount
)

fun StationEntity.toModel() = Station(
    id = id,
    name = name,
    address = address,
    country = country,
    deviceCount = deviceCount
)

fun Device.toEntity(stationId: String) = DeviceEntity(
    id = id,
    stationId = stationId,
    type = type,
    name = name,
    status = status,
    lastSeen = lastSeen
)

fun DeviceEntity.toModel() = Device(
    id = id,
    type = type,
    name = name,
    status = status,
    lastSeen = lastSeen
)
