package com.example.travelgo.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.travelgo.data.remote.dto.PackageDto
import com.example.travelgo.repository.TravelRepository
import com.example.travelgo.ui.state.HomeUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mockApplication: Application
    private lateinit var mockRepository: TravelRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockApplication = mockk(relaxed = true)
        every { mockApplication.applicationContext } returns mockApplication

        mockRepository = mockk()

        viewModel = HomeViewModel(mockApplication, mockRepository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `estado inicial debe ser Idle`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is HomeUiState.Idle)
        }
    }

    @Test
    fun `loadPackages exitoso debe emitir Success`() = runTest {
        val fakeList = emptyList<PackageDto>()

        coEvery { mockRepository.packages() } returns Result.success(fakeList)

        viewModel.loadPackages()
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is HomeUiState.Success)
        }
    }

    @Test
    fun `loadPackages con error debe emitir Error`() = runTest {
        coEvery { mockRepository.packages() } returns Result.failure(Exception("Error de red"))

        viewModel.loadPackages()
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is HomeUiState.Error)
        }
    }
}
