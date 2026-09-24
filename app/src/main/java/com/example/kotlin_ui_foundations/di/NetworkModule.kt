package com.example.kotlin_ui_foundations.di

import com.example.kotlin_ui_foundations.BuildConfig
import com.example.kotlin_ui_foundations.data.remote.interceptor.AuthInterceptor
import com.example.kotlin_ui_foundations.data.remote.token.TokenManager
import com.example.kotlin_ui_foundations.data.remote.token.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Módulo de Hilt que provee e inyecta la infraestructura de red (OkHttp, Retrofit y la gestión de Tokens JWT).
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkTokenModule {

    @Binds
    @Singleton
    abstract fun bindTokenManager(
        tokenManagerImpl: TokenManagerImpl,
    ): TokenManager
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Configuramos el interceptor de logs para registrar las peticiones HTTP completas solo en entornos de depuración
    // para evitar exponer payloads o tokens sensibles en la consola del dispositivo en versión de producción.
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    // Configuramos el cliente OkHttp incorporando el AuthInterceptor primero para adjuntar la cabecera Authorization
    // y posteriormente el HttpLoggingInterceptor para registrar la petición saliente ya autenticada.
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Usamos Kotlinx Serialization flexibilizando la deserialización para tolerar la incorporación de nuevos atributos
    // en el backend en Spring Boot sin que la app falle por claves desconocidas.
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    // Construimos la instancia de Retrofit usando la URL base declarada en local.properties / BuildConfig
    // desacoplando el endpoint del código fuente.
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
