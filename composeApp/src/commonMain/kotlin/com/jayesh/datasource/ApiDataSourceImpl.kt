package com.jayesh.datasource

import com.jayesh.data.Note
import com.jayesh.db.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ApiDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val noteDao: NoteDao
) : ApiDataSource {
    override suspend fun addNote(note: Note): Boolean =
        withContext(dispatcher) {
            noteDao.insert(note)
            true
        }

    override suspend fun updateNote(note: Note): Boolean =
        withContext(dispatcher) {
            noteDao.update(note)
            true
        }

    override suspend fun deleteNote(note: Note): Boolean =
        withContext(dispatcher) {
            noteDao.delete(note)
            true
        }

    override suspend fun getAllNotes(): List<Note> =
        withContext(dispatcher) {
            noteDao.getAllNotes()
        }

}