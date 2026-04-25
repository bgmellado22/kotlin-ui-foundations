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

    fun addComponent(name: String, description: String) {
        viewModelScope.launch {
            try {
                dao.insertComponent(UIComponentEntity(name = name, description = description))
            } catch (e: Exception) {
                // In a real app, you might want to handle this via a side effect or a specific UI state for addition
            }
        }
    }
}
