package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.api.RetrofitClient
import com.example.uinavegacion.data.model.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteTripRepository {

    private val api = RetrofitClient.movipetApi

    suspend fun getAllTrips(): Result<List<Trip>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllTrips()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createTrip(trip: Trip): Result<Trip> = withContext(Dispatchers.IO) {
        try {
            val response = api.createTrip(trip)
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
