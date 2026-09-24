package com.example.kotlin_ui_foundations.data.remote

import com.example.kotlin_ui_foundations.data.model.UserRole
import com.example.kotlin_ui_foundations.data.remote.token.TokenManagerImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TokenManagerTest {

    private lateinit var tokenManager: TokenManagerImpl

    @Before
    fun setup() {
        tokenManager = TokenManagerImpl()
    }

    @Test
    fun `saveSession updates token, user role and authentication state`() {
        assertNull(tokenManager.getToken())
        assertNull(tokenManager.getUserRole())
        assertFalse(tokenManager.getSessionFlow().value.isAuthenticated)

        tokenManager.saveSession("test_jwt_token", UserRole.INSPECTOR)

        assertEquals("test_jwt_token", tokenManager.getToken())
        assertEquals(UserRole.INSPECTOR, tokenManager.getUserRole())
        assertTrue(tokenManager.getSessionFlow().value.isAuthenticated)
    }

    @Test
    fun `clearSession removes token and resets user role`() {
        tokenManager.saveSession("test_jwt_token", UserRole.CIUDADANO)
        tokenManager.clearSession()

        assertNull(tokenManager.getToken())
        assertNull(tokenManager.getUserRole())
        assertFalse(tokenManager.getSessionFlow().value.isAuthenticated)
    }
}
