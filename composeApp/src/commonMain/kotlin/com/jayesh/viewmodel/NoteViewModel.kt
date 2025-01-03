package com.jayesh.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.jayesh.data.Note
import com.jayesh.datasource.ApiDataSource
import com.jayesh.datasource.ApiDataSourceImpl
import com.jayesh.db.NoteDao
import com.jayesh.db.NoteDatabase
import com.jayesh.db.getRoomDatabase
import com.jayesh.repository.NoteRepository
import com.jayesh.repository.NoteRepositoryImpl
import com.jayesh.ui.state.NoteEditorUiState
import com.jayesh.ui.state.NoteUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoteViewModel(
    noteDatabaseBuilder: RoomDatabase.Builder<NoteDatabase>,
) : ViewModel() {
    private val _noteUiState = MutableStateFlow<NoteUiState>(NoteUiState.Loading)
    val noteUiState: StateFlow<NoteUiState> = _noteUiState
    private val _noteEditorUiState = MutableStateFlow<NoteEditorUiState>(NoteEditorUiState.Success)
    val noteEditorUiState: StateFlow<NoteEditorUiState> = _noteEditorUiState

    private var noteDao: NoteDao = getRoomDatabase(noteDatabaseBuilder).getDao()
    private val apiDataSource: ApiDataSource = ApiDataSourceImpl(noteDao = noteDao)
    private val noteRepository: NoteRepository = NoteRepositoryImpl(apiDataSource = apiDataSource)
    val noteTitle: MutableState<String> = mutableStateOf("")
    val noteContent: MutableState<String> = mutableStateOf("")

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

    fun addNote(newNote: Note) {
        viewModelScope.launch {
            try {
                val title = newNote.title.trim()
                val content = newNote.content.trim()

                var titleError: String? = null
                var contentError: String? = null

                if (title.isEmpty()) titleError = "Title cannot be empty."

                if (content.isEmpty()) contentError = "Content cannot be empty."

                if (titleError != null || contentError != null) {
                    _noteEditorUiState.value = NoteEditorUiState.Error(
                        titleMessage = titleError,
                        contentMessage = contentError
                    )
                } else {
                    noteRepository.addNote(newNote)
                        .collectLatest {
                            if (it) fetchNotes()
                        }
                }
                clearNoteEditor()
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while adding the note")
            }
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            try {
                val title = updatedNote.title.trim()
                val content = updatedNote.content.trim()

                var titleError: String? = null
                var contentError: String? = null

                if (title.isEmpty()) titleError = "Title cannot be empty."

                if (content.isEmpty()) contentError = "Content cannot be empty."

                if (titleError != null || contentError != null) {
                    _noteEditorUiState.value = NoteEditorUiState.Error(
                        titleMessage = titleError,
                        contentMessage = contentError
                    )
                } else {
                    noteRepository.updateNote(updatedNote)
                        .collectLatest {
                            if (it) fetchNotes()
                        }
                }
                clearNoteEditor()
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
                        if (it) fetchNotes()
                    }
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error(e.message ?: "An error occurred while deleting the note")
            }
        }
    }

    fun clearNoteEditor() {
        noteTitle.value = ""
        noteContent.value = ""
    }
}