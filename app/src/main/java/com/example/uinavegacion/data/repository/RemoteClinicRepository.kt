package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.api.RetrofitClient
import com.example.uinavegacion.data.model.Clinic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteClinicRepository {

    private val api = RetrofitClient.movipetApi

    suspend fun getAllClinics(): Result<List<Clinic>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllClinics()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createClinic(clinic: Clinic): Result<Clinic> = withContext(Dispatchers.IO) {
        try {
            val response = api.createClinic(clinic)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
