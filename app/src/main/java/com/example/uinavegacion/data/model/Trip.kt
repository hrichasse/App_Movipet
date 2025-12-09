package com.example.uinavegacion.data.model

import com.google.gson.annotations.SerializedName

data class Trip(
    val id: String? = null,
    val fromAddress: String,
    val toAddress: String,
    val fromLat: Double? = null,
    val fromLon: Double? = null,
    val toLat: Double? = null,
    val toLon: Double? = null,
    val driverName: String,
    val driverCar: String,
    val distance: Double,         // km, obligatorio > 0
    val duration: Int,            // minutos, obligatorio > 0
    val cost: Double? = null,     // >= 0
    val rating: Int? = null,      // 1-5
    val comment: String? = null,
    val petId: Long? = null,
    val veterinaryName: String? = null,
    val timestamp: Long? = null   // millis, auto-generado por backend
)
