package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.api.MovipetApi
import com.example.uinavegacion.data.model.Pet

class RemotePetRepository(
    private val api: MovipetApi
) {

    suspend fun getAllPets(): List<Pet> {
        return api.getAllPets()
    }

    suspend fun createPet(pet: Pet): Pet {
        return api.createPet(pet)
    }

    suspend fun getPetById(id: String): Pet {
        return api.getPetById(id)
    }
}
