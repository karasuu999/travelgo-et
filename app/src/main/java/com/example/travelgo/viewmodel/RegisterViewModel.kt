package com.example.travelgo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.travelgo.ui.state.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Validador puro Kotlin (sirve en unit tests)
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    fun onNameChange(newName: String) {
        name = newName
        _nameError.value = if (newName.isBlank()) "El nombre es obligatorio" else null
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        _emailError.value = when {
            newEmail.isEmpty() -> "El email es obligatorio"
            !EMAIL_REGEX.matches(newEmail) -> "Email inválido"
            else -> null
        }
    }

    fun onPasswordChange(newPass: String) {
        password = newPass
        _passwordError.value = when {
            newPass.isEmpty() -> "La contraseña es obligatoria"
            newPass.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }

    fun onConfirmPasswordChange(newConfirm: String) {
        confirmPassword = newConfirm
        _confirmPasswordError.value = when {
            newConfirm.isEmpty() -> "Debe confirmar la contraseña"
            newConfirm != password -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun register() {
        // Reforzamos validaciones por si se llama sin haber escrito nada
        onNameChange(name)
        onEmailChange(email)
        onPasswordChange(password)
        onConfirmPasswordChange(confirmPassword)

        if (_nameError.value != null ||
            _emailError.value != null ||
            _passwordError.value != null ||
            _confirmPasswordError.value != null
        ) {
            _uiState.value = RegisterUiState.Error("Hay errores en el formulario")
            return
        }

        // Versión SIN coroutines: todo es sincrónico
        _uiState.value = RegisterUiState.Loading
        _uiState.value = RegisterUiState.Success
    }
}
