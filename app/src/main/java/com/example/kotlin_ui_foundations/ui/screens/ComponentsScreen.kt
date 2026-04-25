package com.example.kotlin_ui_foundations.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlin_ui_foundations.data.local.UIComponentEntity
import com.example.kotlin_ui_foundations.ui.common.UiState
import com.example.kotlin_ui_foundations.ui.components.FoundationCard

@Composable
fun ComponentsScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: ComponentsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addNextDrawing()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Dibujo")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    MainContent(
                        components = state.data,
                        onItemClick = { onNavigateToDetail(it.id) },
                        onFavoriteClick = { componenteClickeado ->
                            viewModel.toggleFavorite(componenteClickeado)
                        }
                    )
                }
                is UiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    components: List<UIComponentEntity>,
    onItemClick: (UIComponentEntity) -> Unit,
    onFavoriteClick: (UIComponentEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Galería de Dibujos",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Añade nuevos dibujos a la galería y espera a que se revelen.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(components, key = { it.id }) { entity ->
            // FoundationCard con Coil
            FoundationCard(
                title = entity.name,
                description = entity.description,
                imageUrl = entity.imageUrl,
                isFavorite = entity.isFavorite,
                onClick = { onItemClick(entity) },
                onFavoriteClick = { onFavoriteClick(entity) }
            )
        }
    }
}