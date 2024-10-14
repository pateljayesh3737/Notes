package com.jayesh.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jayesh.data.Note
import com.jayesh.generateRandomUuid
import com.jayesh.getCurrnetLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import notes.composeapp.generated.resources.Res
import notes.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditor(
    note: Note? = null,
    onSave: (Note) -> Unit,
    onCancel: () -> Unit,
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    val openAlertDialog = remember { mutableStateOf(false) }
    val colors by remember {
        mutableStateOf(
            listOf(
                0xff374a67,
                0xff068AB2,
                0xfff2392c,
                0xff89BD9E,
                0xff6564DB,
                0xffCB9CF2,
                0xff616283,
            )
        )
    }
    var selectedColor by remember { mutableStateOf(note?.backgroundColor ?: colors.first()) }

    when {
        openAlertDialog.value -> {
            ColorPickerDialog(
                colors = colors, onDismissRequest = {
                    openAlertDialog.value = openAlertDialog.value.not()
                },
                onColorPick = { pickedColor ->
                    selectedColor = pickedColor
                    openAlertDialog.value = openAlertDialog.value.not()
                }
            )
        }

        else -> {
            MaterialTheme(colorScheme = lightColorScheme(primary = Color(selectedColor))) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Notes") },
                            actions = {
                                IconButton(onClick = {
                                    openAlertDialog.value = true
                                }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.compose_multiplatform),
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
                                            backgroundColor = selectedColor
                                        ) ?: Note(
                                                id = generateRandomUuid(),
                                                title = title,
                                                content = content,
                                                createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                                                updatedAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
                                                backgroundColor = selectedColor
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
