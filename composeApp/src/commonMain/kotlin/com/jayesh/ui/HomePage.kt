package com.jayesh.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.jayesh.ui.state.NoteUiState

@Composable
@ExperimentalMaterial3Api
fun HomePage(noteUiState: NoteUiState, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add")
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->

        when (noteUiState) {
            is NoteUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
            }

            is NoteUiState.Error -> {
                val message = noteUiState.message
                Text(text = message, color = Color.Red, modifier = Modifier.padding(paddingValues))
            }

            is NoteUiState.Empty -> {
                Text(text = "No notes available.", modifier = Modifier.padding(paddingValues))
            }

            is NoteUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(noteUiState.notes) { note ->
                        NoteItem(
                            note,
                            onNoteItemClicked = {
                                navController.navigate("edit/${note.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}
