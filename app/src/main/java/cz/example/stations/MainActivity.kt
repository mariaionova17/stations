package cz.example.stations

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cz.example.stations.ui.theme.StationsTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.example.stations.data.model.Station
import cz.example.stations.data.remote.RetrofitClient
import cz.example.stations.ui.screens.StationDetailScreen
import cz.example.stations.ui.screens.StationsScreen
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StationsTheme {
                val navController = rememberNavController()
                var stations by remember {
                    mutableStateOf(emptyList<Station>())
                }

                LaunchedEffect(Unit) {
                    try {
                        stations = RetrofitClient.api.getStations()
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Log.e("StationsAPI", "Failed to load stations", e)
                    }
                }

                Scaffold { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "stations",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("stations") {
                            StationsScreen(
                                stations = stations,
                                onStationClick = { station ->
                                    navController.navigate("details/${station.id}")
                                }
                            )
                        }

                        composable("details/{stationId}") { backStackEntry ->
                            val stationId = backStackEntry.arguments
                                ?.getString("stationId")
                                .orEmpty()

                            StationDetailScreen(
                                stationId = stationId,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}