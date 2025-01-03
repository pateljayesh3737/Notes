package com.jayesh.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.room.RoomDatabase
import com.jayesh.db.NoteDatabase
import com.jayesh.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(noteDatabaseBuilder: RoomDatabase.Builder<NoteDatabase>) {
    AppTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            MainScreen(noteDatabaseBuilder = noteDatabaseBuilder)
        }
    }
}