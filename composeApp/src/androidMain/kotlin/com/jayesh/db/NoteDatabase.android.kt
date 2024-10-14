package com.jayesh.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<NoteDatabase> {
  val appContext = ctx.applicationContext
  val dbFile = appContext.getDatabasePath("my_room.db")
  return Room.databaseBuilder<NoteDatabase>(
    context = appContext,
    name = dbFile.absolutePath
  )
}
