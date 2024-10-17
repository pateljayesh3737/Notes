package com.jayesh.ui.state

sealed class NoteEditorUiState {
    data class Error(val message: String) : NoteEditorUiState()
}