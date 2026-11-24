package cz.example.stations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cz.example.stations.ui.theme.StationsTheme
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.example.stations.ui.screens.StationDetailScreen
import cz.example.stations.ui.screens.StationsScreen
import cz.example.stations.ui.viewmodel.StationsDetailViewModel
import cz.example.stations.ui.viewmodel.StationsViewModel

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            val stationsViewModel = ViewModelProvider(this)[StationsViewModel::class.java]

            setContent {
            StationsTheme {
                val navController = rememberNavController()

                Scaffold { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "stations",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("stations") {
                            StationsScreen(
                                stations = stationsViewModel.stations,
                                onStationClick = { station ->
                                    navController.navigate("details/${station.id}")
                                }
                            )
                        }

                        composable("details/{stationId}") { backStackEntry ->
                            val stationId = backStackEntry.arguments
                                ?.getString("stationId")
                                .orEmpty()

                            val viewModel = ViewModelProvider(backStackEntry)[
                                StationsDetailViewModel::class.java
                            ]

                            LaunchedEffect(stationId) {
                                viewModel.loadDevices(stationId)
                            }

                            StationDetailScreen(
                                stationId = stationId,
                                devices = viewModel.devices,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}