package com.jayesh.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jayesh.data.Note
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

@Composable
fun NoteEditor(
    note: Note? = null, // Pass null for adding a new note
    onSave: (Note) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 10
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newNote = note?.copy(title = title, content = content) ?: Note(
                    id = generateRandomUuid(),
                    title = title,
                    content = content,
                    createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                    updatedAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                )
                onSave(newNote)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
