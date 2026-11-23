package cz.example.stations.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.example.stations.data.model.Device
import cz.example.stations.data.model.Station
import cz.example.stations.data.remote.RetrofitClient
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun StationDetailScreen(
    stationId: String,
    onBack: () -> Unit
) {
    var devices by remember(stationId) {
        mutableStateOf(emptyList<Device>())
    }

    LaunchedEffect(stationId) {
        try {
            devices = RetrofitClient.api.getDevices(stationId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("DevicesAPI", "Failed to load devices", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Back")
        }

        Text(
            text = "Station Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Station ID: $stationId")

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(devices, key = { it.id }) { device ->
                DeviceCard(device)
            }
        }
    }
}

@Composable
fun DeviceCard(device: Device) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = device.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text("Type: ${device.type}")
            Text("Status: ${device.status}")
            Text("Last seen: ${device.lastSeen ?: "Unknown"}")
        }
    }
}

