package com.jayesh.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jayesh.data.Note
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import com.jayesh.ui.theme.AppTheme
import com.jayesh.ui.theme.option.AppThemeOption
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import notes.composeapp.generated.resources.Res
import notes.composeapp.generated.resources.color_palete
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditor(
    note: Note? = null,
    onSave: (Note) -> Unit,
    onNoteDelete: Note?.() -> Unit,
    onCancel: () -> Unit,
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    val openAlertDialog = remember { mutableStateOf(false) }
    val appThemeOptions by remember {
        mutableStateOf(
            listOf(
                AppThemeOption.Default,
                AppThemeOption.Red,
                AppThemeOption.Green,
                AppThemeOption.Blue,
                AppThemeOption.Yellow,
            )
        )
    }
    var selectedAppThemeOption by remember { mutableStateOf(note?.appThemeOption ?: AppThemeOption.Default) }

    when {
        openAlertDialog.value -> {
            ColorPickerDialog(
                appThemeOptions = appThemeOptions, onDismissRequest = {
                    openAlertDialog.value = openAlertDialog.value.not()
                },
                onThemeOptionSelected = { pickedColor ->
                    selectedAppThemeOption = pickedColor
                    openAlertDialog.value = openAlertDialog.value.not()
                }
            )
        }

        else -> {
            AppTheme(themeOption = selectedAppThemeOption, darkTheme = false) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Notes") },
                            actions = {
                                IconButton(onClick = {
                                    openAlertDialog.value = true
                                }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.color_palete),
                                        contentDescription = null
                                    )
                                }
                                IconButton(onClick = {
                                    note?.onNoteDelete()
                                    onCancel()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
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
                                    val newNote =
                                        note?.copy(
                                            title = title,
                                            content = content,
                                            appThemeOption = selectedAppThemeOption
                                        ) ?: Note(
                                            id = generateRandomUuid(),
                                            title = title,
                                            content = content,
                                            createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                                            updatedAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                                            appThemeOption = selectedAppThemeOption
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
                }
            }
        }
    }
}
