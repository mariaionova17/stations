package cz.example.stations.data.repository

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cz.example.stations.data.local.AppDatabase
import cz.example.stations.data.local.DeviceEntity
import cz.example.stations.data.local.StationEntity
import cz.example.stations.data.local.toModel
import cz.example.stations.data.model.Device
import cz.example.stations.data.model.Station
import cz.example.stations.data.remote.StationsApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StationRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: StationRepository

    @Before
    fun setup() {
        val context = InstrumentationRegistry
            .getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        val api = object : StationsApi {
            override suspend fun getStations(): List<Station> {
                throw IOException("No internet")
            }

            override suspend fun getDevices(
                stationId: String
            ): List<Device> = emptyList()
        }

        repository = StationRepository(api, database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun networkFailurePreservesCachedStations() = runBlocking {
        val station = StationEntity(
            id = "station-1",
            name = "Praha – Chodov",
            address = "Test address",
            country = "CZ",
            deviceCount = 3
        )

        database.stationDao().upsertStations(listOf(station))

        try {
            repository.getStations()
            fail("Expected an IOException")
        } catch (_: IOException) {
            // Expected network failure
        }

        val cachedStations = database.stationDao()
            .getStations()
            .first()

        assertEquals(listOf(station), cachedStations)
    }


    @Test
    fun successfulRefreshReplacesCachedStations() = runBlocking {
        val oldStation = StationEntity(
            id = "station-1",
            name = "Old Station",
            address = "Old address",
            country = "CZ",
            deviceCount = 2
        )

        database.stationDao().upsertStations(listOf(oldStation))

        val api = object : StationsApi {
            override suspend fun getStations(): List<Station> {
                return listOf(
                    Station(
                        id = "station-2",
                        name = "New Station",
                        address = "New address",
                        country = "CZ",
                        deviceCount = 3
                    )
                )
            }

            override suspend fun getDevices(
                stationId: String
            ): List<Device> = emptyList()
        }

        val repository = StationRepository(api, database)

        repository.getStations()

        val cachedStations = database.stationDao()
            .getStations()
            .first()

        assertEquals(1, cachedStations.size)
        assertEquals("station-2", cachedStations[0].id)
        assertEquals("New Station", cachedStations[0].name)
    }


    @Test
    fun refreshRemovesDevicesOfDeletedStations() = runBlocking {
        val oldStation = StationEntity(
            id = "station-1",
            name = "Old Station",
            address = "Old address",
            country = "CZ",
            deviceCount = 1
        )

        val existingStation = StationEntity(
            id = "station-2",
            name = "Existing Station",
            address = "Existing address",
            country = "CZ",
            deviceCount = 1
        )

        database.stationDao().upsertStations(
            listOf(oldStation, existingStation)
        )

        val oldDevice = DeviceEntity(
            id = "device-1",
            stationId = "station-1",
            type = "POS",
            name = "Old Device",
            status = "ONLINE",
            lastSeen = null
        )

        val existingDevice = DeviceEntity(
            id = "device-2",
            stationId = "station-2",
            type = "POS",
            name = "Existing Device",
            status = "ONLINE",
            lastSeen = null
        )

        database.deviceDao().upsertDevices(
            listOf(oldDevice, existingDevice)
        )

        val api = object : StationsApi {
            override suspend fun getStations(): List<Station> {
                return listOf(existingStation.toModel())
            }

            override suspend fun getDevices(
                stationId: String
            ): List<Device> = emptyList()
        }

        val repository = StationRepository(api, database)

        repository.getStations()

        val oldDevices = database.deviceDao()
            .getDevices("station-1").first()

        val existingDevices = database.deviceDao()
            .getDevices("station-2").first()

        assertEquals(emptyList<DeviceEntity>(), oldDevices)
        assertEquals(listOf(existingDevice), existingDevices)
    }
}