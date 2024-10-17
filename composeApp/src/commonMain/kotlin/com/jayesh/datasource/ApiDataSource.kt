package com.jayesh.datasource

import com.jayesh.data.Note

interface ApiDataSource {
    suspend fun addNote(note: Note): Boolean
    suspend fun updateNote(note: Note): Boolean
    suspend fun deleteNote(note: Note): Boolean
    suspend fun getAllNotes(): List<Note>
}