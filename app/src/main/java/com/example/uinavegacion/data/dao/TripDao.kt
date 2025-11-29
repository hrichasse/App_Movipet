package com.example.uinavegacion.data.dao

import androidx.room.*
import com.example.uinavegacion.data.entity.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trips ORDER BY timestamp DESC")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getTripById(id: Long): TripEntity?

    @Query("SELECT * FROM trips ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentTrips(limit: Int = 10): Flow<List<TripEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity): Long

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Delete
    suspend fun deleteTrip(trip: TripEntity)

    @Query("SELECT AVG(rating) FROM trips WHERE rating IS NOT NULL")
    suspend fun getAverageRating(): Double?
}
