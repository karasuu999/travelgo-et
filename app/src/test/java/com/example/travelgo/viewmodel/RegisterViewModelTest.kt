package com.example.travelgo.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.travelgo.ui.state.RegisterUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mockApplication: Application
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockApplication = mockk(relaxed = true)
        every { mockApplication.applicationContext } returns mockApplication

        viewModel = RegisterViewModel(mockApplication)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // 1) Nombre vacío -> error
    @Test
    fun `nombre vacío debe mostrar error`() = runTest {
        viewModel.onNameChange("")

        viewModel.nameError.test {
            assertEquals("El nombre es obligatorio", awaitItem())
        }
    }

    // 2) Email inválido -> error
    @Test
    fun `email inválido debe mostrar error`() = runTest {
        viewModel.onEmailChange("correo-malo")

        viewModel.emailError.test {
            val value = awaitItem()
            assertTrue(value?.contains("Email inválido") == true)
        }
    }

    // 3) Password corta -> error
    @Test
    fun `password corta debe mostrar error`() = runTest {
        viewModel.onPasswordChange("123")

        viewModel.passwordError.test {
            assertEquals("La contraseña debe tener al menos 6 caracteres", awaitItem())
        }
    }

    // 4) ConfirmPassword distinta -> error
    @Test
    fun `confirmacion distinta debe mostrar error`() = runTest {
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("654321")

        viewModel.confirmPasswordError.test {
            assertEquals("Las contraseñas no coinciden", awaitItem())
        }
    }

    // 5) Estado inicial -> Idle
    @Test
    fun `estado inicial debe ser Idle`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is RegisterUiState.Idle)
        }
    }

    // 6) isPasswordVisible inicial -> false y toggle funciona
    @Test
    fun `togglePasswordVisibility cambia estado correctamente`() = runTest {
        viewModel.isPasswordVisible.test {
            assertFalse(awaitItem())      // false inicial

            viewModel.togglePasswordVisibility()
            assertTrue(awaitItem())       // true

            viewModel.togglePasswordVisibility()
            assertFalse(awaitItem())      // false otra vez
        }
    }

    // 7) Datos válidos -> Success
    @Test
    fun `registro con datos válidos debe terminar en Success`() = runTest {
        viewModel.onNameChange("Usuario Test")
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("123456")

        viewModel.register()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is RegisterUiState.Success)
        }
    }
}
