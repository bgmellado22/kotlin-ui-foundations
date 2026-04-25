package com.example.kotlin_ui_foundations.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.kotlin_ui_foundations.data.local.ComponentDao
import com.example.kotlin_ui_foundations.data.local.UIComponentEntity
import com.example.kotlin_ui_foundations.navigation.ComponentDetail
import com.example.kotlin_ui_foundations.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComponentDetailViewModel @Inject constructor(
    private val dao: ComponentDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val detailRoute = savedStateHandle.toRoute<ComponentDetail>()
    private val componentId = detailRoute.id

    private val _uiState = MutableStateFlow<UiState<UIComponentEntity>>(UiState.Loading)
    val uiState: StateFlow<UiState<UIComponentEntity>> = _uiState.asStateFlow()

    init {
        loadComponent()
    }

    private fun loadComponent() {
        viewModelScope.launch {
            val component = dao.getComponentById(componentId)
            if (component != null) {
                _uiState.value = UiState.Success(component)
            } else {
                _uiState.value = UiState.Error("Component not found")
            }
        }
    }
}
