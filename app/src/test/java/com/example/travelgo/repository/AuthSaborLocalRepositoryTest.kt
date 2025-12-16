package com.example.travelgo.repository

import android.content.Context
import com.example.travelgo.data.local.SessionManager
import com.example.travelgo.data.remote.ApiService
import com.example.travelgo.data.remote.RetrofitClient
import com.example.travelgo.data.remote.dto.LoginRequest
import com.example.travelgo.data.remote.dto.LoginResponse
import com.example.travelgo.data.remote.dto.ReservationRequest
import com.example.travelgo.data.remote.dto.ReservationResponse
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

/**
 * Tests para TravelRepository (archivo de pruebas unitarias).
 *
 * Cubre:
 * - Login exitoso
 * - Login con error de red
 * - Reserva exitosa
 * - Logout limpia la sesión
 */
class AuthSaborLocalRepositoryTest {

    // Mocks
    private lateinit var mockContext: Context
    private lateinit var mockRetrofit: Retrofit
    private lateinit var mockApi: ApiService

    // System Under Test
    private lateinit var repository: TravelRepository

    @Before
    fun setup() {
        mockContext = mockk(relaxed = true)
        mockRetrofit = mockk()
        mockApi = mockk()

        // Mockear RetrofitClient.build(context) para que use nuestro Retrofit falso
        mockkObject(RetrofitClient)
        every { RetrofitClient.build(any()) } returns mockRetrofit
        every { mockRetrofit.create(ApiService::class.java) } returns mockApi

        // Mockear SessionManager para no usar DataStore real
        mockkConstructor(SessionManager::class)
        coEvery { anyConstructed<SessionManager>().setToken(any()) } just Runs
        coEvery { anyConstructed<SessionManager>().clear() } just Runs

        repository = TravelRepository(mockContext)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    // ==================== LOGIN TESTS ====================

    @Test
    fun `login exitoso debe retornar Success y guardar token`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password123"

        val response = LoginResponse(
            authToken = "mock_token_123",
            token = null,
            user = null
        )

        coEvery { mockApi.login(LoginRequest(email, password)) } returns response

        // When
        val result = repository.login(email, password)

        // Then
        assertTrue("El login debe ser Success", result.isSuccess)
        coVerify { anyConstructed<SessionManager>().setToken("mock_token_123") }
    }

    @Test
    fun `login con error de red debe retornar Failure`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password123"

        coEvery { mockApi.login(any()) } throws Exception("Network error")

        // When
        val result = repository.login(email, password)

        // Then
        assertTrue("El login debe ser Failure", result.isFailure)
    }

    // ==================== RESERVA TESTS ====================

    @Test
    fun `reserve exitoso debe llamar createReservation y retornar Success`() = runBlocking {
        // Given
        val packageId = 10
        val name = "Cliente Test"
        val email = "cliente@example.com"
        val travelers = 2

        val apiResponse = ReservationResponse(
            reservation_id = 1,
            status = "ok",
            message = "Reserva creada"
        )

        coEvery {
            mockApi.createReservation(
                ReservationRequest(
                    package_id = packageId,
                    customer_name = name,
                    customer_email = email,
                    travelers = travelers
                )
            )
        } returns apiResponse

        // When
        val result = repository.reserve(
            packageId = packageId,
            name = name,
            email = email,
            travelers = travelers
        )

        // Then
        assertTrue(result.isSuccess)
        val res = result.getOrNull()
        assertEquals(1, res?.reservation_id)
        assertEquals("ok", res?.status)
    }

    // ==================== LOGOUT TESTS ====================

    @Test
    fun `logout debe limpiar SessionManager`() = runBlocking {
        // When
        val result = repository.logout()

        // Then
        assertTrue(result.isSuccess)
        coVerify { anyConstructed<SessionManager>().clear() }
    }
}
