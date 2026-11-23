package cz.example.stations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StationsScreen(stations: List<Station>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Stations",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(stations, key = { it.id }) { station ->
            StationCard(station)
        }
    }
}

@Composable
fun StationCard(station: Station) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = station.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = station.address,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Devices: ${station.deviceCount}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
