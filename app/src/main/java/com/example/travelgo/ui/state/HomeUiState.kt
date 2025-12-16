package com.example.travelgo.ui.home

/**
 * Estados de UI para la Home
 */
sealed class HomeUiState {

    object Loading : HomeUiState()

    data class Success(
        val packages: List<Any> = emptyList() // cambia Any por tu modelo real si lo tienes
    ) : HomeUiState()

    data class Error(
        val message: String
    ) : HomeUiState()
}
