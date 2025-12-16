package com.example.travelgo.viewmodel

import android.net.Uri
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    // 1) Estado inicial correcto
    @Test
    fun `estado inicial debe tener isLoading true y avatarUri null`() = runTest {
        val viewModel = ProfileViewModel()

        val state = viewModel.uiState.value
        assertTrue(state.isLoading)
        assertNull(state.avatarUri)
        assertNull(state.user)
        assertNull(state.error)
    }

    // 2) updateAvatar con URI válida debe actualizar avatarUri
    @Test
    fun `updateAvatar con uri válida debe actualizar avatarUri`() = runTest {
        val viewModel = ProfileViewModel()
        val fakeUri: Uri = mockk(relaxed = true)   // 👈 sin Uri.parse

        viewModel.updateAvatar(fakeUri)

        val state = viewModel.uiState.value
        assertEquals(fakeUri, state.avatarUri)
    }

    // 3) updateAvatar con null debe limpiar avatarUri
    @Test
    fun `updateAvatar con null debe limpiar avatarUri`() = runTest {
        val viewModel = ProfileViewModel()
        val fakeUri: Uri = mockk(relaxed = true)

        // Primero seteamos un avatar
        viewModel.updateAvatar(fakeUri)
        // Luego lo limpiamos
        viewModel.updateAvatar(null)

        val state = viewModel.uiState.value
        assertNull(state.avatarUri)
    }

    // 4) updateAvatar solo modifica avatarUri y mantiene el resto del estado
    @Test
    fun `updateAvatar solo modifica avatarUri y mantiene el resto del estado`() = runTest {
        val viewModel = ProfileViewModel()

        val initial = viewModel.uiState.value
        val fakeUri: Uri = mockk(relaxed = true)

        viewModel.updateAvatar(fakeUri)

        val updated = viewModel.uiState.value

        // Campos que NO deberían cambiar
        assertEquals(initial.isLoading, updated.isLoading)
        assertEquals(initial.user, updated.user)
        assertEquals(initial.error, updated.error)
        assertEquals(initial.formattedCreatedAt, updated.formattedCreatedAt)

        // Campo que SÍ debe cambiar
        assertEquals(fakeUri, updated.avatarUri)
    }
}
