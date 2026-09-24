package com.example.kotlin_ui_foundations.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_ui_foundations.data.model.UserRole
import com.example.kotlin_ui_foundations.data.remote.token.TokenManager
import com.example.kotlin_ui_foundations.data.remote.token.UserSessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// ViewModel encargado de gestionar el estado de sesión y la evaluación del rol autenticado.
// Mantiene reactiva la sesión para gatillar actualizaciones inmediatas en la pila de navegación de Compose.
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {

    val sessionState: StateFlow<UserSessionState> = tokenManager.getSessionFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSessionState(),
        )

    // Establece la sesión activa con el token y rol seleccionados para gatillar la navegación según perfil.
    fun login(role: UserRole) {
        val sampleToken = "sample_jwt_token_${System.currentTimeMillis()}"
        tokenManager.saveSession(token = sampleToken, role = role)
    }

    // Elimina la sesión en memoria para retornar inmediatamente al flujo público de autenticación.
    fun logout() {
        tokenManager.clearSession()
    }
}
