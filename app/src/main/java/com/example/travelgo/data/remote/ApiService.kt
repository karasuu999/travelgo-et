package com.example.travelgo.data.remote

import com.example.travelgo.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * API contract para la TravelGo API (NestJS + Mongo).
 */
interface ApiService {

    // ---------- AUTH ----------

    /**
     * Login:
     *  POST /auth/login
     *  body: { "email": "...", "password": "..." }
     *  response: { success, message, data: { user, access_token } }
     */
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    /**
     * Perfil del usuario autenticado:
     *  GET /auth/profile
     *  response: { success, data: User }
     *
     * Aquí devolvemos directamente el UserDto (solo data).
     */
    @GET("auth/profile")
    suspend fun profile(): UserDto

    // ---------- PAQUETES TURÍSTICOS ----------

    /**
     * Listar paquetes:
     *  GET /paquete-turistico
     *  response: { success, data: [PaqueteTuristico], total }
     */
    @GET("paquete-turistico")
    suspend fun listPackages(): PackagesResponse

    /**
     * Detalle de paquete:
     *  GET /paquete-turistico/{id}
     *  response: { success, data: PaqueteTuristico }
     */
    @GET("paquete-turistico/{id}")
    suspend fun getPackage(@Path("id") id: String): PackageDto

    // ---------- RESERVAS ----------

    /**
     * Crear reserva:
     *  POST /reserva
     *  body: CreateReservaDto (al menos "nombre" obligatorio)
     *  response: { success, message, data: Reserva }
     */
    @POST("reserva")
    suspend fun createReservation(@Body body: ReservationRequest): ReservationResponse
}
