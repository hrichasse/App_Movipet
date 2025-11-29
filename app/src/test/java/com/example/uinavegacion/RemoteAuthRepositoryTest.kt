package com.example.uinavegacion.data.repository

import com.example.uinavegacion.data.api.MovipetApi
import com.example.uinavegacion.data.model.LoginRequest
import com.example.uinavegacion.data.model.User
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteAuthRepositoryTest {

    private lateinit var api: MovipetApi
    private lateinit var repository: RemoteAuthRepository

    @Before
    fun setUp() {
        // MovipetApi falso (mock)
        api = Mockito.mock(MovipetApi::class.java)
        // Repositorio usando el mock
        repository = RemoteAuthRepository(api)
    }

    @Test
    fun `login exitoso devuelve User`() = runBlocking {
        // GIVEN
        val request = LoginRequest(
            email = "test@movipet.com",
            password = "123456"
        )

        val expectedUser = User(
            id = "123",
            name = "Test User",
            email = "test@movipet.com",
            password = null
        )

        whenever(api.loginUser(any())).thenReturn(Response.success(expectedUser))

        // WHEN
        val result = repository.login(request)

        // THEN
        assertEquals(expectedUser.id, result.id)
        assertEquals(expectedUser.name, result.name)
        assertEquals(expectedUser.email, result.email)
    }

    @Test
    fun `login con error http lanza Exception con mensaje adecuado`() = runBlocking {
        // GIVEN
        val request = LoginRequest(
            email = "noexiste@movipet.com",
            password = "123456"
        )

        val errorJson = """{"message":"Usuario no encontrado"}"""
        val responseBody = ResponseBody.create(null, errorJson
        )

        whenever(api.loginUser(any())).thenReturn(
            Response.error(401, responseBody)
        )

        // WHEN + THEN
        try {
            repository.login(request)
            assertTrue("Se esperaba una Exception y no se lanzó", false)
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("Error en login"))
            assertTrue(e.message!!.contains("401"))
        }
    }
}
