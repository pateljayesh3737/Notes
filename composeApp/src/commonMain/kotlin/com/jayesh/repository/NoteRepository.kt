package com.jayesh.repository

import com.jayesh.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note): Flow<Boolean>
    suspend fun updateNote(note: Note): Flow<Boolean>
    suspend fun deleteNote(note: Note): Flow<Boolean>
    suspend fun getAllNotes() : Flow<List<Note>>
}