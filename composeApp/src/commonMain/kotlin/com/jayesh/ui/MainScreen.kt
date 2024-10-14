package com.jayesh.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import com.jayesh.db.NoteDatabase
import com.jayesh.db.getRoomDatabase
import com.jayesh.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(noteDatabaseBuilder: RoomDatabase.Builder<NoteDatabase>) {
    val noteDao = getRoomDatabase(builder = noteDatabaseBuilder).getDao()
    val navController = rememberNavController()

    val noteViewModel: NoteViewModel = viewModel { NoteViewModel(noteDao) }
    val notes by noteViewModel.notes.collectAsState()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomePage(notes = notes, navController)
        }
        composable("add") {
            NoteEditor(
                onSave = { newNote ->
                    noteViewModel.addNote(newNote.title, newNote.content)
                    navController.navigate("home")
                },
                onCancel = {
                    navController.navigate("home")
                }
            )
        }
        composable("edit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            val note = notes.find { it.id == noteId }
            NoteEditor(
                note = note,
                onSave = { updatedNote ->
                    if (note != null) {
                        noteViewModel.updateNote(updatedNote)
                    }
                    navController.navigate("home")
                },
                onCancel = {
                    navController.navigate("home")
                }
            )
        }
    }
}