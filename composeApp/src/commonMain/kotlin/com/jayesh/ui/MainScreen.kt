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
import com.jayesh.ui.state.NoteUiState
import com.jayesh.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(noteDatabaseBuilder: RoomDatabase.Builder<NoteDatabase>) {
    val navController = rememberNavController()

    val noteViewModel: NoteViewModel = viewModel { NoteViewModel(noteDatabaseBuilder) }
    val noteUiState by noteViewModel.noteUiState.collectAsState()
    val noteEditorUiState by noteViewModel.noteEditorUiState.collectAsState()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomePage(noteUiState = noteUiState, navController)
        }
        composable("add") {
            NoteEditor(
                noteEditorUiState = noteEditorUiState,
                noteTitleState = noteViewModel.noteTitle,
                noteContentState = noteViewModel.noteContent,
                onSave = { newNote ->
                    noteViewModel.addNote(newNote)
                },
                onCancel = {
                    navController.navigate("home")
                },
                onNoteDelete = {
                    if (this !=null) {
                        noteViewModel.deleteNote(note = this)
                    }
                }
            )
        }
        composable("edit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            val note = when (val ob = noteUiState) {
                is NoteUiState.Success -> ob.notes.find { it.id == noteId }
                else -> null
            }
            NoteEditor(
                noteEditorUiState= noteEditorUiState,
                noteTitleState = noteViewModel.noteTitle,
                noteContentState = noteViewModel.noteContent,
                note = note,
                onSave = { updatedNote ->
                    if (note != null) {
                        noteViewModel.updateNote(updatedNote)
                    }
                    navController.navigate("home")
                },
                onCancel = {
                    navController.navigate("home")
                },
                onNoteDelete = {
                    if (this !=null) {
                        noteViewModel.deleteNote(note = this)
                    }
                }
            )
        }
    }
}