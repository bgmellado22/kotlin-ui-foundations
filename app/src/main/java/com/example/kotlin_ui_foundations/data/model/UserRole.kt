package com.example.kotlin_ui_foundations.data.model

// Define los roles del sistema de seguridad comunal para segmentar vistas operativas según nivel de autorización en el backend.
enum class UserRole(val roleName: String) {
    CIUDADANO("ROLE_CIUDADANO"),
    INSPECTOR("ROLE_INSPECTOR");

    companion object {
        fun fromRoleName(roleName: String?): UserRole {
            return entries.find { it.roleName.equals(roleName, ignoreCase = true) } ?: CIUDADANO
        }
    }
}
