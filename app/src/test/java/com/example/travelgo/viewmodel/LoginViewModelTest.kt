package com.example.travelgo.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import com.example.travelgo.ui.state.LoginUiState

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import io.mockk.mockk
import io.mockk.every
import io.mockk.unmockkAll

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mockApplication: Application
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockApplication = mockk(relaxed = true)
        every { mockApplication.applicationContext } returns mockApplication

        viewModel = LoginViewModel(mockApplication)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `email vacío debe mostrar error`() = runTest {
        viewModel.onEmailChange("")

        viewModel.emailError.test {
            assertEquals("El email es obligatorio", awaitItem())
        }
    }

    @Test
    fun `email inválido debe mostrar error`() = runTest {
        viewModel.onEmailChange("invalid-email")

        viewModel.emailError.test {
            assertTrue(awaitItem()?.contains("Email inválido") == true)
        }
    }

    @Test
    fun `email válido no debe mostrar error`() = runTest {
        viewModel.onEmailChange("valid@example.com")

        viewModel.emailError.test {
            assertEquals(null, awaitItem())
        }
    }

    @Test
    fun `password vacío debe mostrar error`() = runTest {
        viewModel.onPasswordChange("")

        viewModel.passwordError.test {
            assertEquals("La contraseña es obligatoria", awaitItem())
        }
    }

    @Test
    fun `togglePasswordVisibility cambia estado correctamente`() = runTest {
        viewModel.isPasswordVisible.test {
            assertFalse(awaitItem())

            viewModel.togglePasswordVisibility()
            assertTrue(awaitItem())

            viewModel.togglePasswordVisibility()
            assertFalse(awaitItem())
        }
    }
}
