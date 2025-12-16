package com.example.travelgo.data.remote

import android.content.Context
import com.example.travelgo.data.local.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // 👇 NUEVA BASE URL: backend desplegado en Render
    //
    // OJO:
    //  - Es https
    //  - Termina en /api/
    //  - No hace falta 10.0.2.2 porque NO es localhost, es un servidor público.
    private const val BASE_URL = "https://booksky-api-l2yi.onrender.com/api/"

    fun build(context: Context): Retrofit {
        val session = SessionManager(context)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(session))  // añade el Bearer token si existe
            .addInterceptor(logging)                  // log de requests/responses
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)                        // 👉 ahora apunta a Render
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
