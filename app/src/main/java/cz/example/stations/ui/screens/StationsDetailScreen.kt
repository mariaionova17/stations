package cz.example.stations.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.example.stations.data.model.Device
import cz.example.stations.ui.components.ErrorBanner
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun StationDetailScreen(
    stationName: String,
    stationAddress: String,
    devices: List<Device>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Station Details",
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (error != null) {
            ErrorBanner(
                message = if (devices.isEmpty()) {
                    "Failed to load devices"
                } else {
                    "Couldn't refresh devices"
                },
                onRetry = onRetry
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stationName,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = stationAddress,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Devices",
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (!isLoading && error == null && devices.isEmpty()) {
                        Text("No devices found")
                    }
                }
            }

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
            Text("Last seen: ${formatLastSeen(device.lastSeen)}")
        }
    }
}

private fun formatLastSeen(lastSeen: String?): String {
    if (lastSeen == null) return "Unknown"

    val instant = Instant.parse(lastSeen)

    return DateTimeFormatter
        .ofPattern("d MMM yyyy, HH:mm", Locale.ENGLISH)
        .withZone(ZoneId.systemDefault())
        .format(instant)
}
