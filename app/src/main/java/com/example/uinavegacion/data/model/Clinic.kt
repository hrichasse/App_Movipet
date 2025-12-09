package com.example.uinavegacion.data.model

import com.google.gson.annotations.SerializedName

data class Clinic(
    val id: String? = null,
    @SerializedName("clinic_name")
    val clinicName: String,
    @SerializedName("clinic_address")
    val clinicAddress: String,
    @SerializedName("clinic_phone")
    val clinicPhone: String,
    @SerializedName("clinic_email")
    val clinicEmail: String,
    val latitude: Double,
    val longitude: Double,
    val address: String
)
