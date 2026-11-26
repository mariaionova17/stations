package cz.example.stations.data.local

import cz.example.stations.data.model.Device
import cz.example.stations.data.model.Station
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EntityMappersTest {
    @Test
    fun `station model maps correctly to entity`() {
        val station = Station(
            id = "station-1",
            name = "Praha – Chodov",
            address = "Roztylská 2321/19, Praha",
            country = "CZ",
            deviceCount = 3
        )

        val expected = StationEntity(
            id = "station-1",
            name = "Praha – Chodov",
            address = "Roztylská 2321/19, Praha",
            country = "CZ",
            deviceCount = 3
        )

        assertEquals(expected, station.toEntity())
    }

    @Test
    fun `station entity maps correctly to model`() {
        val entity = StationEntity(
            id = "station-1",
            name = "Praha – Chodov",
            address = "Roztylská 2321/19, Praha",
            country = "CZ",
            deviceCount = 3
        )

        val expected = Station(
            id = "station-1",
            name = "Praha – Chodov",
            address = "Roztylská 2321/19, Praha",
            country = "CZ",
            deviceCount = 3
        )

        assertEquals(expected, entity.toModel())
    }

    @Test
    fun `device model maps correctly to entity`() {
        val device = Device(
            id = "device-1",
            type = "POS",
            name = "Pokladna 1",
            status = "ONLINE",
            lastSeen = null
        )

        val expected = DeviceEntity(
            id = "device-1",
            stationId = "station-1",
            type = "POS",
            name = "Pokladna 1",
            status = "ONLINE",
            lastSeen = null
        )

        assertEquals(expected, device.toEntity("station-1"))
    }

    @Test
    fun `device entity maps correctly to model`() {
        val entity = DeviceEntity(
            id = "device-1",
            stationId = "station-1",
            type = "POS",
            name = "Pokladna 1",
            status = "ONLINE",
            lastSeen = "2026-11-15T18:42:00Z"
        )

        val expected = Device(
            id = "device-1",
            type = "POS",
            name = "Pokladna 1",
            status = "ONLINE",
            lastSeen = "2026-11-15T18:42:00Z"
        )

        assertEquals(expected, entity.toModel())
    }
}
