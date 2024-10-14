package com.jayesh.data

import kotlinx.datetime.*

data class Note(
    val id: String,            // Unique identifier for the note
    val title: String,         // Title of the note
    val content: String,       // Content/body of the note
    val createdAt: LocalDateTime,  // Date and time when the note was created
    val updatedAt: LocalDateTime,  // Date and time when the note was last updated
    val backgroundColor: Long = 0xffe5dcc5,
    val isSynced: Boolean = false  // Flag to check if the note is synced with the server
)

val now: Instant = Clock.System.now()
val today: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
val sampleNotes = listOf(
    Note(
        id = "1",
        title = "Sample Note 1",
        content = "This is the content of sample note 1.",
        createdAt = today,
        updatedAt = today,
        backgroundColor = 0xffe5dcc5
    ),
    Note(
        id = "2",
        title = "Sample Note 2",
        content = "This is the content of sample note 2.",
        createdAt = today,
        updatedAt = today,
        backgroundColor = 0xff85c7f2
    )
)