package com.example.uinavegacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uinavegacion.data.model.Trip
import com.example.uinavegacion.data.repository.RemoteTripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripViewModel : ViewModel() {

    private val repository = RemoteTripRepository()

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _createSuccess = MutableStateFlow(false)
    val createSuccess: StateFlow<Boolean> = _createSuccess

    private val _lastCreatedTrip = MutableStateFlow<Trip?>(null)
    val lastCreatedTrip: StateFlow<Trip?> = _lastCreatedTrip

    fun loadTrips() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.getAllTrips()
            result.onSuccess { tripsList ->
                _trips.value = tripsList
            }.onFailure { e ->
                _error.value = e.message ?: "Error al cargar viajes"
            }
            _isLoading.value = false
        }
    }

    fun createTrip(trip: Trip) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _createSuccess.value = false
            val result = repository.createTrip(trip)
            result.onSuccess { createdTrip ->
                _lastCreatedTrip.value = createdTrip
                _createSuccess.value = true
                loadTrips() // recargar histórico
            }.onFailure { e ->
                _error.value = e.message ?: "Error al crear viaje"
            }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun resetCreateSuccess() {
        _createSuccess.value = false
    }
}
