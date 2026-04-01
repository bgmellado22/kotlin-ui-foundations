package com.example.kotlin_ui_foundations.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_ui_foundations.data.local.ComponentDao
import com.example.kotlin_ui_foundations.data.local.UIComponentEntity
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

    // mutable state and public
    private val _uiState = MutableStateFlow(ComponentsUiState(isLoading = true))
    val uiState: StateFlow<ComponentsUiState> = _uiState.asStateFlow()

    init {
        observeComponents()
    }

    private fun observeComponents() {
        viewModelScope.launch {
            dao.getAllComponents()
                .catch { e -> _uiState.update { it.copy(errorMessage = e.message, isLoading = false) } }
                .collect { list ->
                    _uiState.update { it.copy(components = list, isLoading = false) }
                }
        }
    }

    // add a new component
    fun addComponent(name: String, description: String) {
        viewModelScope.launch {
            dao.insertComponent(UIComponentEntity(name = name, description = description))
        }
    }
}