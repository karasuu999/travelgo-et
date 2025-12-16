package com.example.travelgo.ui.state

sealed class ProfileUiState {
    object Idle : ProfileUiState()
    object Loading : ProfileUiState()
    object LogoutSuccess : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
