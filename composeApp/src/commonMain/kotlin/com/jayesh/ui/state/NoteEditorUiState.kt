package com.jayesh.ui.state

sealed class NoteEditorUiState {
    data class Error(val titleMessage: String? = null, val contentMessage: String? = null) : NoteEditorUiState()
    data object Success : NoteEditorUiState()
}
