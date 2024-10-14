package com.jayesh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayesh.data.Note
import com.jayesh.data.sampleNotes
import com.jayesh.db.NoteDao
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            _notes.value = noteDao.getAllNotes()
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(
                id = generateRandomUuid(),
                title = title,
                content = content,
                createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                updatedAt =  getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
            )
            noteDao.insert(newNote)
            fetchNotes()
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
           try {
                noteDao.update(updatedNote)
                fetchNotes()
           } catch (e: Exception) {
               println(e.message ?: "An error occurred while updating the note")
           }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
            fetchNotes()
        }
    }
}