package com.example.kotlin_ui_foundations.data.model

// Mapeo del enum RolUsuario del backend Spring Boot.
// La app móvil opera exclusivamente con los perfiles CIUDADANO e INSPECTOR.
enum class UserRole(val roleName: String) {
    CIUDADANO("CIUDADANO"),
    INSPECTOR("INSPECTOR");

    companion object {
        // Sanitiza la cadena removiendo el prefijo "ROLE_" si Spring Security lo incluye en la cabecera/claims,
        // garantizando compatibilidad exacta tanto con "CIUDADANO"/"INSPECTOR" como con "ROLE_CIUDADANO"/"ROLE_INSPECTOR".
        fun fromRoleName(roleName: String?): UserRole {
            if (roleName.isNullOrBlank()) return CIUDADANO
            val normalizedRole = roleName.trim().removePrefix("ROLE_")
            return entries.find { it.roleName.equals(normalizedRole, ignoreCase = true) } ?: CIUDADANO
        }
    }
}
