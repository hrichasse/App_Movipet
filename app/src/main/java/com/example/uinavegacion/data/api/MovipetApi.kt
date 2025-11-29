package com.example.uinavegacion.data.api

import com.example.uinavegacion.data.model.LoginRequest
import com.example.uinavegacion.data.model.RegisterRequest
import com.example.uinavegacion.data.model.User
import com.example.uinavegacion.data.model.Pet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovipetApi {

    // ---------- AUTH ----------

    @POST("api/auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<User>

    @POST("api/auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<User>

    // ---------- PETS ----------

    @GET("api/pets")
    suspend fun getAllPets(): Response<List<Pet>>

    @POST("api/pets")
    suspend fun createPet(
        @Body pet: Pet
    ): Response<Pet>

    @GET("api/pets/{id}")
    suspend fun getPetById(
        @Path("id") id: String
    ): Response<Pet>
}
