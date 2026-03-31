package com.example.kotlin_ui_foundations.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlin_ui_foundations.ui.components.FoundationButton
import com.example.kotlin_ui_foundations.ui.components.FoundationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Foundations", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Acción */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        MainContent(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val itemsList = listOf("Componente A", "Componente B", "Componente C", "Componente D")

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Catálogo de Cimientos",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(itemsList) { itemTitle ->
            FoundationCard(title = itemTitle) {
                Text("Descripción detallada del $itemTitle usando nuestra tipografía moderna.")
                FoundationButton(
                    text = "Ver Detalle",
                    onClick = { /* Navegación */ },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}