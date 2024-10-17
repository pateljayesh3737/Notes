package com.jayesh.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jayesh.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [Note::class], version = 2)
@ConstructedBy(NoteDatabaseConstructor::class)
abstract class NoteDatabase : RoomDatabase() {
  abstract fun getDao(): NoteDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NoteDatabaseConstructor : RoomDatabaseConstructor<NoteDatabase> {
    override fun initialize(): NoteDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<NoteDatabase>
): NoteDatabase {
  return builder
      .fallbackToDestructiveMigration(true)
      .setDriver(BundledSQLiteDriver())
      .setQueryCoroutineContext(Dispatchers.IO)
      .build()
}