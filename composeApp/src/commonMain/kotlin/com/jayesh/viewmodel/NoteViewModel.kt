package com.jayesh.viewmodel

import com.jayesh.datasource.ApiDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.jayesh.data.Note
import com.jayesh.datasource.ApiDataSourceImpl
import com.jayesh.db.NoteDao
import com.jayesh.db.NoteDatabase
import com.jayesh.db.getRoomDatabase
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import com.jayesh.repository.NoteRepository
import com.jayesh.repository.NoteRepositoryImpl
import com.jayesh.ui.state.NoteUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

class NoteViewModel(
    noteDatabaseBuilder: RoomDatabase.Builder<NoteDatabase>,
) : ViewModel() {
    private val _noteUiState = MutableStateFlow<NoteUiState>(NoteUiState.Loading)
    val noteUiState: StateFlow<NoteUiState> = _noteUiState

    private var noteDao: NoteDao = getRoomDatabase(noteDatabaseBuilder).getDao()
    private val apiDataSource: ApiDataSource = ApiDataSourceImpl(noteDao = noteDao)
    private val noteRepository: NoteRepository = NoteRepositoryImpl(apiDataSource = apiDataSource)

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                noteRepository.getAllNotes()
                    .collectLatest { notes ->
                        _noteUiState.value = if (notes.isEmpty()) {
                            NoteUiState.Empty
                        } else {
                            NoteUiState.Success(notes)
                        }
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
                noteRepository.addNote(newNote)
                    .collectLatest {
                        if (it) {
                            fetchNotes()
                        }
                    }
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while adding the note")
            }
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            try {
                noteRepository.updateNote(updatedNote)
                    .collectLatest {
                        if (it) {
                            fetchNotes()
                        }
                    }
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while updating the note")
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(note)
                    .collectLatest {
                        if (it) {
                            fetchNotes()
                        }
                    }
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while deleting the note")
            }
        }
    }
}