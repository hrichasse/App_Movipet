package com.example.uinavegacion.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TripSelectionViewModel : ViewModel() {
    private val _selectedPetType = MutableStateFlow("Perro")
    val selectedPetType: StateFlow<String> = _selectedPetType

    private val _selectedVehicleType = MutableStateFlow("Van mediana")
    val selectedVehicleType: StateFlow<String> = _selectedVehicleType

    private val _selectedOrigin = MutableStateFlow("Tu ubicación actual")
    val selectedOrigin: StateFlow<String> = _selectedOrigin

    private val _selectedDestination = MutableStateFlow("Veterinaria cercana")
    val selectedDestination: StateFlow<String> = _selectedDestination

    fun setPetType(petType: String) {
        _selectedPetType.value = petType
    }

    fun setVehicleType(vehicleType: String) {
        _selectedVehicleType.value = vehicleType
    }

    fun setOrigin(origin: String) {
        _selectedOrigin.value = origin
    }

    fun setDestination(destination: String) {
        _selectedDestination.value = destination
    }

    fun resetSelection() {
        _selectedPetType.value = "Perro"
        _selectedVehicleType.value = "Van mediana"
        _selectedOrigin.value = "Tu ubicación actual"
        _selectedDestination.value = "Veterinaria cercana"
    }
}
