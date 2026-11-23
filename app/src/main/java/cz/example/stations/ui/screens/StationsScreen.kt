package cz.example.stations.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.example.stations.data.model.Station

@Composable
fun StationsScreen(
    stations: List<Station>,
    onStationClick: (Station) -> Unit,
    modifier: Modifier = Modifier
){
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
            StationCard(
                station,
                onClick = { onStationClick(station) }
            )
        }
    }
}

@Composable
fun StationCard(station: Station, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
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
