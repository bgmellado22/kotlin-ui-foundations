package com.example.kotlin_ui_foundations.navigation

import kotlinx.serialization.Serializable

// Destinos fuertemente tipados con Kotlinx Serialization para la navegación condicional según rol del usuario.
@Serializable
object LoginRoute

@Serializable
object CiudadanoHomeRoute

@Serializable
object InspectorHomeRoute

// Destinos de soporte para componentes base
@Serializable
object Home

@Serializable
data class ComponentDetail(val id: Int)
