package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.api.MovipetApi
import com.example.uinavegacion.data.model.LoginRequest
import com.example.uinavegacion.data.model.RegisterRequest
import com.example.uinavegacion.data.model.User

class RemoteAuthRepository(
    private val api: MovipetApi
) {

    suspend fun register(name: String, email: String, password: String): User {
        val body = RegisterRequest(name = name, email = email, password = password)
        return api.registerUser(body)
    }

    suspend fun login(email: String, password: String): User {
        val body = LoginRequest(email = email, password = password)
        return api.login(body)
    }
}