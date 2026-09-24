package com.example.kotlin_ui_foundations.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserRoleTest {

    @Test
    fun `fromRoleName correctly parses raw Spring Boot enum names without ROLE_ prefix`() {
        assertEquals(UserRole.CIUDADANO, UserRole.fromRoleName("CIUDADANO"))
        assertEquals(UserRole.INSPECTOR, UserRole.fromRoleName("INSPECTOR"))
    }

    @Test
    fun `fromRoleName correctly parses Spring Security authorities with ROLE_ prefix`() {
        assertEquals(UserRole.CIUDADANO, UserRole.fromRoleName("ROLE_CIUDADANO"))
        assertEquals(UserRole.INSPECTOR, UserRole.fromRoleName("ROLE_INSPECTOR"))
    }

    @Test
    fun `fromRoleName is case insensitive`() {
        assertEquals(UserRole.CIUDADANO, UserRole.fromRoleName("ciudadano"))
        assertEquals(UserRole.INSPECTOR, UserRole.fromRoleName("inspector"))
    }

    @Test
    fun `fromRoleName defaults to CIUDADANO for null or unknown role names`() {
        assertEquals(UserRole.CIUDADANO, UserRole.fromRoleName(null))
        assertEquals(UserRole.CIUDADANO, UserRole.fromRoleName("OPERADOR"))
        assertEquals(UserRole.CIUDADANO, UserRole.fromRoleName("ADMINISTRADOR"))
    }
}
