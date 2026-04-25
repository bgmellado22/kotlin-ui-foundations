package com.example.kotlin_ui_foundations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kotlin_ui_foundations.navigation.ComponentDetail
import com.example.kotlin_ui_foundations.navigation.Home
import com.example.kotlin_ui_foundations.ui.screens.ComponentDetailScreen
import com.example.kotlin_ui_foundations.ui.screens.ComponentsScreen
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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            ComponentsScreen(
                onNavigateToDetail = { id ->
                    navController.navigate(ComponentDetail(id = id))
                }
            )
        }
        composable<ComponentDetail> {
            ComponentDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
