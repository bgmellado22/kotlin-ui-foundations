package com.example.kotlin_ui_foundations.data.remote.token

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// Contrato para la gestión centralizada del JWT Token necesario en la comunicación REST.
interface TokenManager {
    fun getToken(): String?
    fun saveToken(token: String)
    fun clearToken()
    fun getTokenFlow(): StateFlow<String?>
}

// Implementación Thread-Safe del TokenManager usando StateFlow.
// Se mantiene en memoria para proveer acceso instantáneo y síncrono al AuthInterceptor de OkHttp.
@Singleton
class TokenManagerImpl @Inject constructor() : TokenManager {

    private val _tokenFlow = MutableStateFlow<String?>(null)

    override fun getToken(): String? = _tokenFlow.value

    override fun saveToken(token: String) {
        _tokenFlow.value = token
    }

    override fun clearToken() {
        _tokenFlow.value = null
    }

    override fun getTokenFlow(): StateFlow<String?> = _tokenFlow.asStateFlow()
}
