package com.example.travelgo.ui.profile

import android.net.Uri

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val avatarUri: Uri? = null,
    val userName: String = "Usuario TravelGo",
    val userEmail: String = "usuario@travelgo.com"
)
