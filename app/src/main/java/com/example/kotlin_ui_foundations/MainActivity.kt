package com.example.kotlin_ui_foundations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_ui_foundations.ui.screens.ComponentsScreen
import com.example.kotlin_ui_foundations.ui.theme.KotlinuifoundationsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // to receive injected data
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinuifoundationsTheme {
                ComponentsScreen()
            }
        }
    }
}