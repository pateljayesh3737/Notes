package com.jayesh.ui.state

import com.jayesh.data.Note

sealed class NoteUiState {
    data object Loading : NoteUiState()
    data class Success(val notes: List<Note>) : NoteUiState()
    data class Error(val message: String) : NoteUiState()
    data object Empty : NoteUiState()
}