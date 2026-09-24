package com.example.kotlin_ui_foundations.data.remote

import com.example.kotlin_ui_foundations.data.model.UserRole
import com.example.kotlin_ui_foundations.data.remote.interceptor.AuthInterceptor
import com.example.kotlin_ui_foundations.data.remote.token.TokenManagerImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {

    private lateinit var tokenManager: TokenManagerImpl
    private lateinit var authInterceptor: AuthInterceptor
    private lateinit var chain: Interceptor.Chain

    @Before
    fun setup() {
        tokenManager = TokenManagerImpl()
        authInterceptor = AuthInterceptor(tokenManager)
        chain = mockk(relaxed = true)
    }

    @Test
    fun `when token is present, request includes Authorization Bearer header`() {
        tokenManager.saveSession("sample_secret_jwt", UserRole.INSPECTOR)

        val originalRequest = Request.Builder()
            .url("https://sgs-backend-lcof.onrender.com/api/v1/incidentes")
            .build()

        val requestSlot = slot<Request>()
        every { chain.request() } returns originalRequest
        every { chain.proceed(capture(requestSlot)) } returns mockk<Response>(relaxed = true)

        authInterceptor.intercept(chain)

        val interceptedRequest = requestSlot.captured
        assertEquals("Bearer sample_secret_jwt", interceptedRequest.header("Authorization"))
    }

    @Test
    fun `when token is absent, request has no Authorization header`() {
        tokenManager.clearSession()

        val originalRequest = Request.Builder()
            .url("https://sgs-backend-lcof.onrender.com/api/v1/auth/login")
            .build()

        val requestSlot = slot<Request>()
        every { chain.request() } returns originalRequest
        every { chain.proceed(capture(requestSlot)) } returns mockk<Response>(relaxed = true)

        authInterceptor.intercept(chain)

        val interceptedRequest = requestSlot.captured
        assertNull(interceptedRequest.header("Authorization"))
    }
}
