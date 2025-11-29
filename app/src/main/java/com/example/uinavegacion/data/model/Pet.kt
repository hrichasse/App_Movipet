package com.example.uinavegacion.data.model

data class Pet(
    val id: String? = null,         // id de MongoDB (String)
    val name: String,
    val type: String,
    val breed: String,
    val age: Int,
    val weight: Double,
    val createdAt: Long? = null
)