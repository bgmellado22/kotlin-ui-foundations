package com.example.kotlin_ui_foundations.data.remote.token

import com.example.kotlin_ui_foundations.data.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// Estado inmutable de la sesión activa para reactividad en la capa de UI y navegación condicional.
data class UserSessionState(
    val token: String? = null,
    val role: UserRole? = null,
) {
    val isAuthenticated: Boolean get() = !token.isNullOrBlank()
}

// Contrato para la gestión centralizada del JWT Token y del rol de usuario necesario en el filtrado de vistas.
interface TokenManager {
    fun getToken(): String?
    fun getUserRole(): UserRole?
    fun saveSession(token: String, role: UserRole)
    fun clearSession()
    fun getSessionFlow(): StateFlow<UserSessionState>
}

// Mantiene en memoria de forma thread-safe las credenciales de sesión para actualizar reactivamente la navegación.
@Singleton
class TokenManagerImpl @Inject constructor() : TokenManager {

    private val _sessionState = MutableStateFlow(UserSessionState())

    override fun getToken(): String? = _sessionState.value.token

    override fun getUserRole(): UserRole? = _sessionState.value.role

    override fun saveSession(token: String, role: UserRole) {
        _sessionState.value = UserSessionState(token = token, role = role)
    }

    override fun clearSession() {
        _sessionState.value = UserSessionState()
    }

    override fun getSessionFlow(): StateFlow<UserSessionState> = _sessionState.asStateFlow()
}
