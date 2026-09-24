package com.example.kotlin_ui_foundations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_ui_foundations.data.model.UserRole
import com.example.kotlin_ui_foundations.navigation.CiudadanoHomeRoute
import com.example.kotlin_ui_foundations.navigation.InspectorHomeRoute
import com.example.kotlin_ui_foundations.navigation.LoginRoute
import com.example.kotlin_ui_foundations.ui.auth.AuthViewModel
import com.example.kotlin_ui_foundations.ui.auth.LoginScreen
import com.example.kotlin_ui_foundations.ui.ciudadano.CiudadanoHomeScreen
import com.example.kotlin_ui_foundations.ui.inspector.InspectorHomeScreen
import com.example.kotlin_ui_foundations.ui.theme.KotlinuifoundationsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinuifoundationsTheme {
                AppNavigation()
            }
        }
    }
}

// Configuración del grafo de navegación con evaluación reactiva del rol (ROLE_CIUDADANO vs ROLE_INSPECTOR).
// Desacopla los destinos y limpia la pila de actividades al cambiar de estado de autenticación para seguridad del sistema.
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val sessionState by authViewModel.sessionState.collectAsStateWithLifecycle()

    // Redirección asíncrona ante cambios en el estado de autenticación o en el rol asignado
    LaunchedEffect(sessionState.isAuthenticated, sessionState.role) {
        if (!sessionState.isAuthenticated) {
            navController.navigate(LoginRoute) {
                popUpTo(0) { inclusive = true }
            }
        } else {
            when (sessionState.role) {
                UserRole.INSPECTOR -> navController.navigate(InspectorHomeRoute) {
                    popUpTo(0) { inclusive = true }
                }
                UserRole.CIUDADANO, null -> navController.navigate(CiudadanoHomeRoute) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginClick = { role ->
                    authViewModel.login(role)
                },
            )
        }

        composable<CiudadanoHomeRoute> {
            CiudadanoHomeScreen(
                onLogoutClick = {
                    authViewModel.logout()
                },
            )
        }

        composable<InspectorHomeRoute> {
            InspectorHomeScreen(
                onLogoutClick = {
                    authViewModel.logout()
                },
            )
        }
    }
}
