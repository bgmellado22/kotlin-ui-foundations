package com.example.kotlin_ui_foundations.ui.screens

import com.example.kotlin_ui_foundations.data.local.UIComponentEntity

data class ComponentsUiState(
    val components: List<UIComponentEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)