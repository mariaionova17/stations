package cz.example.stations.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.example.stations.data.model.Device
import cz.example.stations.data.repository.StationRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class StationsDetailViewModel: ViewModel() {
    private val repository = StationRepository()
    var devices by mutableStateOf(emptyList<Device>())
        private set

    fun loadDevices(stationId: String){
        viewModelScope.launch() {
            devices = emptyList()
            try {
                devices = repository.getDevices(stationId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("DevicesAPI", "Failed to load devices", e)
            }
        }
    }
}