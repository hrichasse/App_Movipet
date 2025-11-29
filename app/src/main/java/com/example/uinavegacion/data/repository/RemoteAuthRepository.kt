package com.example.uinavegacion.data.repository


import com.example.uinavegacion.data.api.MovipetApi
import com.example.uinavegacion.data.model.LoginRequest
import com.example.uinavegacion.data.model.RegisterRequest
import com.example.uinavegacion.data.model.User

class RemoteAuthRepository(
    private val api: MovipetApi
) {

    // REGISTRO
    suspend fun register(request: RegisterRequest): User {
        val response = api.registerUser(request)   // <- Response<User>

        if (response.isSuccessful) {
            val user = response.body()
            if (user != null) {
                return user                        // <- devolvemos User
            } else {
                throw Exception("El servidor respondió sin cuerpo (User nulo) en registro.")
            }
        } else {
            val error = response.errorBody()?.string()
            throw Exception("Error en registro: ${response.code()} - $error")
        }
    }

    // LOGIN
    suspend fun login(request: LoginRequest): User {
        val response = api.loginUser(request)      // <- Response<User>

        if (response.isSuccessful) {
            val user = response.body()
            if (user != null) {
                return user
            } else {
                throw Exception("El servidor respondió sin cuerpo (User nulo) en login.")
            }
        } else {
            val error = response.errorBody()?.string()
            throw Exception("Error en login: ${response.code()} - $error")
        }
    }
}
