package com.example.kotlin_ui_foundations.data.remote.interceptor

import com.example.kotlin_ui_foundations.data.remote.token.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// Interceptamos la petición OkHttp para inyectar el Bearer Token;
// el backend de Spring Boot es Stateless y descarta automáticamente las solicitudes no firmadas.
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()

        // Si no hay token guardado (ej. durante la petición pública de login),
        // enviamos la solicitud sin alterar el header Authorization.
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
