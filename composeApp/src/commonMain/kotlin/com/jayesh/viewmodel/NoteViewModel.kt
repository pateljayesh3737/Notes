package com.jayesh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayesh.data.Note
import com.jayesh.db.NoteDao
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import com.jayesh.ui.state.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {
    private val _noteUiState = MutableStateFlow<NoteUiState>(NoteUiState.Loading)
    val noteUiState: StateFlow<NoteUiState> = _noteUiState

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            try {
                val notes = noteDao.getAllNotes()
                _noteUiState.value = if (notes.isEmpty()) {
                    NoteUiState.Empty
                } else {
                    NoteUiState.Success(notes)
                }
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while fetching notes")
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(
                id = generateRandomUuid(),
                title = title,
                content = content,
                createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                updatedAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
            )
            try {
                noteDao.insert(newNote)
                fetchNotes()
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while adding the note")
            }
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            try {
                noteDao.update(updatedNote)
                fetchNotes()
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while updating the note")
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                noteDao.delete(note)
                fetchNotes()
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while deleting the note")
            }
        }
    }
}