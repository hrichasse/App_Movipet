package com.example.uinavegacion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String, // perro, gato, etc
    val breed: String? = null,
    val age: Int? = null,
    val weight: Double? = null,
    val createdAt: Long = System.currentTimeMillis()
)
