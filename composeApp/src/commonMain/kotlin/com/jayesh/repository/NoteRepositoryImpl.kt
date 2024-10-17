package com.jayesh.repository

import com.jayesh.data.Note
import com.jayesh.datasource.ApiDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository {
    override suspend fun addNote(note: Note): Flow<Boolean> {
        return flow {
            apiDataSource.addNote(note)
            emit(true)
        }
    }

    override suspend fun updateNote(note: Note): Flow<Boolean> {
        return flow {
            apiDataSource.updateNote(note)
            emit(true)
        }
    }

    override suspend fun deleteNote(note: Note): Flow<Boolean> {
        return flow {
            apiDataSource.deleteNote(note)
            emit(true)
        }
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return flow {
            emit(apiDataSource.getAllNotes())
        }
    }
}