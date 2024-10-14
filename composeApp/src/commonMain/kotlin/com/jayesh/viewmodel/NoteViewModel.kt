package com.jayesh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayesh.data.Note
import com.jayesh.data.sampleNotes
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel: ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(sampleNotes)
    val notes: StateFlow<List<Note>> = _notes

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(
                id = generateRandomUuid(),
                title = title,
                content = content,
                createdAt = getCurrnetLocalDateTime(),
                updatedAt =  getCurrnetLocalDateTime(),
            )
            _notes.value += newNote
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            _notes.value = _notes.value.map { note ->
                if (note.id == updatedNote.id) updatedNote else note
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            _notes.value = _notes.value.filterNot { it.id == noteId }
        }
    }

    fun fetchNotes(): List<Note> {
        return sampleNotes
    }
}