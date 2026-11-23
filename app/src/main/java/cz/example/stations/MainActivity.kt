package cz.example.stations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.example.stations.ui.theme.StationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stations = listOf(
            Station(
                id = "1",
                name = "Praha – Chodov",
                address = "Roztylská 2321/19, 148 00 Praha 11",
                country = "CZ",
                deviceCount = 3
            ),
            Station(
                id = "2",
                name = "Demo Station – Brno",
                address = "Sample address, Brno",
                country = "CZ",
                deviceCount = 5
            ),
            Station(
                id = "3",
                name = "Demo Station – Ostrava",
                address = "Sample address, Ostrava",
                country = "CZ",
                deviceCount = 0
            )
        )

        setContent {
            StationsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StationsScreen(
                        stations = stations,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StationsTheme {
        Greeting("Android")
    }
}