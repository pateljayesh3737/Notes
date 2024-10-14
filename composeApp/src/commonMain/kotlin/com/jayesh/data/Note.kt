package com.jayesh.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jayesh.getCurrnetLocalDateTime
import kotlinx.datetime.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String,            // Unique identifier for the note
    val title: String,         // Title of the note
    val content: String,       // Content/body of the note
    val createdAt: String,  // Date and time when the note was created
    val updatedAt: String,  // Date and time when the note was last updated
    val backgroundColor: Long = 0xffe5dcc5,
    val isSynced: Boolean = false  // Flag to check if the note is synced with the server
)

val sampleNotes = listOf(
    Note(
        id = "1",
        title = "Sample Note 1",
        content = "This is the content of sample note 1.",
        createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
        updatedAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
        backgroundColor = 0xffe5dcc5
    ),
    Note(
        id = "2",
        title = "Sample Note 2",
        content = "This is the content of sample note 2.",
        createdAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
        updatedAt = getCurrnetLocalDateTime().format(LocalDateTime.Formats.ISO),
        backgroundColor = 0xff85c7f2
    )
)