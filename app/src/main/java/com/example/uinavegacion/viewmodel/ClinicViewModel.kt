package com.example.uinavegacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uinavegacion.data.model.Clinic
import com.example.uinavegacion.data.repository.RemoteClinicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClinicViewModel : ViewModel() {

    private val repository = RemoteClinicRepository()

    private val _clinics = MutableStateFlow<List<Clinic>>(emptyList())
    val clinics: StateFlow<List<Clinic>> = _clinics

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _createSuccess = MutableStateFlow(false)
    val createSuccess: StateFlow<Boolean> = _createSuccess

    fun loadClinics() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.getAllClinics()
            result.onSuccess { clinicsList ->
                _clinics.value = clinicsList
            }.onFailure { e ->
                _error.value = e.message ?: "Error al cargar clínicas"
            }
            _isLoading.value = false
        }
    }

    fun createClinic(clinic: Clinic) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _createSuccess.value = false
            val result = repository.createClinic(clinic)
            result.onSuccess {
                _createSuccess.value = true
                loadClinics() // recargar lista
            }.onFailure { e ->
                _error.value = e.message ?: "Error al crear clínica"
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
