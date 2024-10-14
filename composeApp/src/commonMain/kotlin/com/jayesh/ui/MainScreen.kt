package com.jayesh.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jayesh.db.NoteDatabase
import com.jayesh.db.getRoomDatabase
import com.jayesh.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val noteViewModel: NoteViewModel = viewModel { NoteViewModel() }
    val notes = noteViewModel.notes.collectAsState().value

    NavHost(navController, startDestination = "home") {
        composable("home") { HomePage(notes = notes, navController) }
        composable("edit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            val note = notes.find { it.id == noteId }
            NoteEditor(
                note = note,
                onSave = { updatedNote ->
                    noteViewModel.updateNote(updatedNote)
                    navController.navigate("home")
                },
                onCancel = {
                    navController.navigate("home")
                }
            )
        }
    }
}