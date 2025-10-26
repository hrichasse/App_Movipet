package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.dao.PetDao
import com.example.uinavegacion.data.entity.PetEntity
import kotlinx.coroutines.flow.Flow

class PetRepository(private val petDao: PetDao) {
    val allPets: Flow<List<PetEntity>> = petDao.getAllPets()

    suspend fun insertPet(pet: PetEntity): Long {
        return petDao.insertPet(pet)
    }

    suspend fun updatePet(pet: PetEntity) {
        petDao.updatePet(pet)
    }

    suspend fun deletePet(pet: PetEntity) {
        petDao.deletePet(pet)
    }

    suspend fun getPetById(id: Long): PetEntity? {
        return petDao.getPetById(id)
    }
}
