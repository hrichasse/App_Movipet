package com.example.uinavegacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uinavegacion.data.api.RetrofitClient
import com.example.uinavegacion.data.model.Pet
import com.example.uinavegacion.data.repository.RemotePetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class RemotePetViewModel : ViewModel() {

    // Usa RetrofitClient.api (o .movipetApi si así lo nombraste)
    private val repository = RemotePetRepository(RetrofitClient.movipetApi)

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadPets() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getAllPets()
                _pets.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createPet(pet: Pet) {
        viewModelScope.launch {
            try {
                repository.createPet(pet)
                loadPets() // recarga la lista después de crear
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error al crear mascota"
            }
        }
    }

    fun deletePet(petId: String) {
        viewModelScope.launch {
            try {
                repository.deletePet(petId)
                loadPets() // recarga la lista después de eliminar
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error al eliminar mascota"
            }
        }
    }

    init {
        // Apenas se crea el ViewModel, prueba el backend
        viewModelScope.launch {
            try {
                Log.d("RemotePetVM", "Llamando a backend /api/pets...")
                val result = repository.getAllPets()
                _pets.value = result
                Log.d("RemotePetVM", "Mascotas recibidas: ${result.size}")
                result.forEach { pet ->
                    Log.d("RemotePetVM", "Pet -> ${pet.id} | ${pet.name} | ${pet.type}")
                }
            } catch (e: Exception) {
                Log.e("RemotePetVM", "Error llamando backend", e)
            }
        }
    }
}
