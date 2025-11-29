package com.example.uinavegacion.data.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    // En un caso real no devolverías el password desde el backend,
    // pero para la asignatura lo dejamos simple.
    val password: String? = null
)