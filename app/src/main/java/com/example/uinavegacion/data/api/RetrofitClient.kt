package com.example.uinavegacion.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // 🔥 EMULADOR: usar 10.0.2.2
    // 🔥 DISPOSITIVO FÍSICO: cambiar a tu IP local (ej: "http://192.168.1.5:8080/")
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val movipetApi: MovipetApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovipetApi::class.java)
    }
}