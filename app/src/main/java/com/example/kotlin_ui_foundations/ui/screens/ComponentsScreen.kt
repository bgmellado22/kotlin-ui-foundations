package com.example.kotlin_ui_foundations.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlin_ui_foundations.ui.components.FoundationCard
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.kotlin_ui_foundations.data.local.UIComponentEntity

@Composable
fun ComponentsScreen(
    viewModel: ComponentsViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addComponent("Nuevo", "Creado desde la UI")
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            MainContent(
                components = state.components, // Room list
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun MainContent(
    components: List<UIComponentEntity>,
    modifier: Modifier = Modifier
) {
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

        items(components) { entity ->
            FoundationCard(title = entity.name) {
                Text(
                    text = entity.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (entity.isFavorite) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                }
            }
        }
    }
}