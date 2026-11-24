package cz.example.stations.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.example.stations.data.model.Device

@Composable
fun StationDetailScreen(
    stationId: String,
    devices: List<Device>,
    onBack: () -> Unit
) {
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

