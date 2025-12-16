package com.example.travelgo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelgo.model.User
import com.example.travelgo.repository.TravelRepository
import com.example.travelgo.ui.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = TravelRepository(application)

    // ✅ Añadido para evitar crash en tests unitarios
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    private var email: String = ""
    private var password: String = ""

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible


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
            else -> null
        }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun login() {
        if (email.isEmpty()) {
            _uiState.value = LoginUiState.Error("El email es obligatorio")
            return
        }

        if (password.isEmpty()) {
            _uiState.value = LoginUiState.Error("La contraseña es obligatoria")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = repository.login(email, password)

            _uiState.value = result.fold(
                onSuccess = {
                    LoginUiState.Success(
                        User(
                            id = "",
                            nombre = "",
                            email = email,
                            role = "CLIENTE"
                        )
                    )
                },
                onFailure = { err ->
                    LoginUiState.Error(err.message ?: "Error desconocido")
                }
            )
        }
    }
}
