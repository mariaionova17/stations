package cz.example.stations.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.example.stations.data.model.Device
import cz.example.stations.data.remote.RetrofitClient
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class StationsDetailViewModel: ViewModel() {
    var devices by mutableStateOf(emptyList<Device>())
        private set

    fun loadDevices(stationId: String){
        viewModelScope.launch() {
            devices = emptyList()
            try {
                devices = RetrofitClient.api.getDevices(stationId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("DevicesAPI", "Failed to load devices", e)
            }
        }
    }
}