package com.example.uinavegacion.location

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationClient = LocationClient(application)
    private val geocodingService = GeocodingService(application)

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    private val _address = MutableStateFlow<String?>(null)
    val address: StateFlow<String?> = _address

    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking

    fun start() {
        if (_isTracking.value) return
        _isTracking.value = true
        viewModelScope.launch {
            locationClient.locationUpdates().collect { newLocation ->
                _location.value = newLocation
                // Geocoding inverso automático
                val addr = geocodingService.getAddressFromLocation(newLocation)
                _address.value = addr
            }
        }
    }

    fun stop() {
        _isTracking.value = false
    }

    fun getLastKnownLocation() {
        viewModelScope.launch {
            val lastLocation = locationClient.lastKnownLocation()
            if (lastLocation != null) {
                _location.value = lastLocation
                val addr = geocodingService.getAddressFromLocation(lastLocation)
                _address.value = addr
            }
        }
    }
}
