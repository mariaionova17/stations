package cz.example.stations.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.example.stations.data.model.Station
import cz.example.stations.data.repository.StationRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class StationsViewModel : ViewModel() {
    private val repository = StationRepository()
    var stations by mutableStateOf(emptyList<Station>())
        private set

    init {
        loadStations()
    }

    fun loadStations(){
        viewModelScope.launch() {
            try {
                stations = repository.getStations()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("StationsAPI", "Failed to load stations", e)
            }
        }
    }
}