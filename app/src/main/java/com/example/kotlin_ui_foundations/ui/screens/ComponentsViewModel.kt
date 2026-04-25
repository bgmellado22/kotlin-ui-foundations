package com.example.kotlin_ui_foundations.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_ui_foundations.data.local.ComponentDao
import com.example.kotlin_ui_foundations.data.local.UIComponentEntity
import com.example.kotlin_ui_foundations.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComponentsViewModel @Inject constructor(
    private val dao: ComponentDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<UIComponentEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<UIComponentEntity>>> = _uiState.asStateFlow()

    // Lista limitada de dibujos
    private val miPortafolio = listOf(
        UIComponentEntity(
        name = "Gaara, Sasuke y Naruto",
        description = "Dibujo digital de los tres protagonistas del arco de los exámenes chunnin.",
        imageUrl = "https://drive.google.com/uc?id=1KRW4vlVaEqrGtpd9htPNePIU6duqpxty"
        ),
        UIComponentEntity(
            name = "Nate y Ashley en el Spiderverso",
            description = "Dibujo digital de los protagonistas del manga 'Lost Souls', adaptados a Spiderman",
            imageUrl = "https://drive.google.com/uc?id=1BDiZFYh7B1X1J4eesHsvy81cLeXO4UJc"
        ),
        UIComponentEntity(
            name = "Tiranosaurio Rex",
            description = "Entintado digital de perfil del T-Rex, rey de los terópodos.",
            imageUrl = "https://drive.google.com/uc?id=187tTSVMBIccGXPCCzVTCTUJasnapKVrH"
        ),
        UIComponentEntity(
            name = "Ya está roto...",
            description = "Entintado digital de la memorable escena entre Grace y Tommy, de la serie Peaky Blinders.",
            imageUrl = "https://drive.google.com/uc?id=1GQ-ZIgvcYSS8fyibKJEq48zylNGXyxG0"
        )
    )

    // Contador para saber cuál dibujo toca añadir
    private var indiceActual = 0

    init {
        observeComponents()
    }

    private fun observeComponents() {
        viewModelScope.launch {
            dao.getAllComponents()
                .catch { e -> 
                    _uiState.value = UiState.Error(e.message ?: "Unknown Error")
                }
                .collect { list ->
                    _uiState.value = UiState.Success(list)
                }
        }
    }

    fun addNextDrawing() {
        if (indiceActual < miPortafolio.size) {
            viewModelScope.launch {
                try {
                    val dibujoAgregar = miPortafolio[indiceActual]
                    dao.insertComponent(dibujoAgregar)
                    indiceActual++
                } catch (e: Exception) {
                    _uiState.update { UiState.Error(e.message ?: "Unknown Error") }
                }
            }
        }
    }

    fun toggleFavorite(component: UIComponentEntity) {
        viewModelScope.launch {
            val updatedDrawing = component.copy(isFavorite = !component.isFavorite)
            dao.updateComponent(updatedDrawing)
        }
    }
}
