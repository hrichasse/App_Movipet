package com.example.uinavegacion.data.api

import com.example.uinavegacion.data.model.Pet
import com.example.uinavegacion.data.model.User
import com.example.uinavegacion.data.model.LoginRequest
import com.example.uinavegacion.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovipetApi {

    // --------------------------
    // PETS
    // --------------------------

    // Crear mascota
    @POST("api/pets")
    suspend fun createPet(@Body pet: Pet): Pet

    // Obtener todas las mascotas
    @GET("api/pets")
    suspend fun getAllPets(): List<Pet>

    // Obtener mascota por ID
    @GET("api/pets/{id}")
    suspend fun getPetById(@Path("id") id: String): Pet

    // Actualizar mascota
    @PUT("api/pets/{id}")
    suspend fun updatePet(
        @Path("id") id: String,
        @Body pet: Pet
    ): Pet

    // Eliminar mascota
    @DELETE("api/pets/{id}")
    suspend fun deletePet(@Path("id") id: String)

    // ---------- AUTH / USERS ----------
    @POST("api/users/register")
    suspend fun registerUser(@Body request: RegisterRequest): User

    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): User
}
