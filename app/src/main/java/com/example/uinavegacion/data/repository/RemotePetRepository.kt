package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.api.MovipetApi
import com.example.uinavegacion.data.model.Pet

class RemotePetRepository(
    private val api: MovipetApi
) {

    // LISTAR TODAS LAS MASCOTAS
    suspend fun getAllPets(): List<Pet> {
        val response = api.getAllPets()   // Response<List<Pet>>

        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            val error = response.errorBody()?.string()
            throw Exception("Error obteniendo mascotas: ${response.code()} - $error")
        }
    }

    // CREAR MASCOTA
    suspend fun createPet(pet: Pet): Pet {
        val response = api.createPet(pet) // Response<Pet>

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body
            } else {
                throw Exception("El servidor respondió sin cuerpo al crear mascota.")
            }
        } else {
            val error = response.errorBody()?.string()
            throw Exception("Error creando mascota: ${response.code()} - $error")
        }
    }

    // ELIMINAR MASCOTA
    suspend fun deletePet(id: String) {
        val response = api.deletePet(id)

        if (!response.isSuccessful) {
            val error = response.errorBody()?.string()
            throw Exception("Error eliminando mascota: ${response.code()} - $error")
        }
    }

    // OBTENER MASCOTA POR ID
    suspend fun getPetById(id: String): Pet {
        val response = api.getPetById(id) // Response<Pet>

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body
            } else {
                throw Exception("El servidor respondió sin cuerpo al buscar mascota.")
            }
        } else {
            val error = response.errorBody()?.string()
            throw Exception("Error obteniendo mascota: ${response.code()} - $error")
        }
    }
}
