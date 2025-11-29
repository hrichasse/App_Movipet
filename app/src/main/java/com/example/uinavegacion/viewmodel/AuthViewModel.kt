package com.example.uinavegacion.viewmodel

import com.example.uinavegacion.data.model.LoginRequest
import com.example.uinavegacion.data.model.RegisterRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uinavegacion.data.api.RetrofitClient
import com.example.uinavegacion.data.model.User
import com.example.uinavegacion.data.repository.RemoteAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = RemoteAuthRepository(RetrofitClient.movipetApi)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val user = repository.login(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )
                _currentUser.value = user
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error al iniciar sesión"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val user = repository.register(
                    RegisterRequest(
                        name = name,
                        email = email,
                        password = password
                    )
                )
                _currentUser.value = user
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error al registrar usuario"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // NUEVO: cerrar sesión limpiando estado
    fun logout() {
        _currentUser.value = null
        _error.value = null
        _isLoading.value = false
    }

    fun clearError() {
        _error.value = null
    }
}

