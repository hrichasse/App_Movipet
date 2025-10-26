package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.dao.TripDao
import com.example.uinavegacion.data.entity.TripEntity
import kotlinx.coroutines.flow.Flow

class TripRepository(private val tripDao: TripDao) {
    val allTrips: Flow<List<TripEntity>> = tripDao.getAllTrips()

    fun getRecentTrips(limit: Int = 10): Flow<List<TripEntity>> {
        return tripDao.getRecentTrips(limit)
    }

    suspend fun insertTrip(trip: TripEntity): Long {
        return tripDao.insertTrip(trip)
    }

    suspend fun updateTrip(trip: TripEntity) {
        tripDao.updateTrip(trip)
    }

    suspend fun getTripById(id: Long): TripEntity? {
        return tripDao.getTripById(id)
    }

    suspend fun getAverageRating(): Double? {
        return tripDao.getAverageRating()
    }
}
