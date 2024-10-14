package com.jayesh.data

import kotlinx.datetime.LocalDateTime

data class NoteModel(
    val id: String,            // Unique identifier for the note
    val title: String,         // Title of the note
    val content: String,       // Content/body of the note
    val createdAt: LocalDateTime,  // Date and time when the note was created
    val updatedAt: LocalDateTime,  // Date and time when the note was last updated
    val isSynced: Boolean = false  // Flag to check if the note is synced with the server
)
