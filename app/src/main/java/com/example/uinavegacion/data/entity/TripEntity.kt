package com.example.uinavegacion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fromAddress: String,
    val toAddress: String,
    val fromLat: Double,
    val fromLon: Double,
    val toLat: Double,
    val toLon: Double,
    val driverName: String,
    val driverCar: String,
    val distance: Double, // km
    val duration: Int, // minutos
    val cost: Double,
    val rating: Int? = null,
    val comment: String? = null,
    val petId: Long? = null,
    val veterinaryName: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
