package cz.example.stations.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.example.stations.StationsApplication
import cz.example.stations.data.model.Station
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class StationsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as StationsApplication).repository
    var stations by mutableStateOf(emptyList<Station>())
        private set

    init {
        viewModelScope.launch {
            repository.observeStations().collect {
                stations = it
            }
        }
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