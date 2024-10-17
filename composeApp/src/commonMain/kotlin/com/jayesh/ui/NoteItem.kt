package com.jayesh.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jayesh.data.Note
import com.jayesh.ui.theme.getColorScheme

@Composable
fun NoteItem(
    note: Note,
    onNoteItemClicked: () -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = note.appThemeOption.getColorScheme(isSystemInDarkTheme()).primaryContainer
        ),
        onClick = { onNoteItemClicked() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Created: ${note.createdAt}", style = MaterialTheme.typography.bodySmall)
        }
    }
}