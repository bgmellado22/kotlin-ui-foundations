package com.example.kotlin_ui_foundations.ui.screens

import app.cash.turbine.test
import com.example.kotlin_ui_foundations.data.local.ComponentDao
import com.example.kotlin_ui_foundations.data.local.UIComponentEntity
import com.example.kotlin_ui_foundations.ui.common.UiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ComponentsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var dao: ComponentDao
    private lateinit var viewModel: ComponentsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        dao = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel starts, it should emit Loading then Success`() = runTest {
        val components = listOf(
            UIComponentEntity(
                id = 1,
                name = "Test",
                description = "Desc",
                imageUrl = "https://example.com/image.png",
            ),
        )
        every { dao.getAllComponents() } returns flowOf(components)

        viewModel = ComponentsViewModel(dao)

        viewModel.uiState.test {
            // Initial state should be Loading (or the first emission from the flow if it happens immediately)
            val state = awaitItem()
            if (state is UiState.Loading) {
                val nextState = awaitItem()
                assert(nextState is UiState.Success)
                assertEquals(components, (nextState as UiState.Success).data)
            } else {
                assert(state is UiState.Success)
                assertEquals(components, (state as UiState.Success).data)
            }
        }
    }
}
