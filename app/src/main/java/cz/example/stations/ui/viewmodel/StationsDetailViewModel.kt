package cz.example.stations.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.example.stations.StationsApplication
import cz.example.stations.data.model.Device
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class StationsDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as StationsApplication).repository
    var devices by mutableStateOf(emptyList<Device>())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var error by mutableStateOf<String?>(null)
        private set
    private var observationJob: Job? = null

    fun loadDevices(stationId: String){
        observationJob?.cancel()
        devices = emptyList()

        observationJob = viewModelScope.launch {
            repository.observeDevices(stationId).collect {
                devices = it
            }
        }
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                repository.getDevices(stationId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = "Failed to refresh devices"
                Log.e("DevicesAPI", "Failed to load devices", e)
            } finally {
                isLoading = false
            }
        }
    }
}