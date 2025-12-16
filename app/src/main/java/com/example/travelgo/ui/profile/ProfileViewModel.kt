package com.example.travelgo.ui.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelgo.repository.AvatarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val avatarRepository = AvatarRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadUser()
        loadSavedAvatar()
    }

    fun loadUser() {
        _uiState.update {
            it.copy(
                isLoading = false,
                userName = "Usuario TravelGo",
                userEmail = "usuario@travelgo.com"
            )
        }
    }

    // Lee el avatar guardado en DataStore y actualiza el estado
    private fun loadSavedAvatar() {
        viewModelScope.launch {
            avatarRepository.getAvatarUri().collectLatest { savedUri ->
                _uiState.update { current ->
                    current.copy(avatarUri = savedUri)
                }
            }
        }
    }

    // Se llama cuando el usuario selecciona una nueva imagen
    fun updateAvatar(uri: Uri?) {
        viewModelScope.launch {
            avatarRepository.saveAvatarUri(uri)
            // No hace falta tocar el estado aquí:
            // loadSavedAvatar() ya está escuchando el Flow.
        }
    }

    fun clearAvatar() {
        viewModelScope.launch {
            avatarRepository.clearAvatarUri()
        }
    }
}
