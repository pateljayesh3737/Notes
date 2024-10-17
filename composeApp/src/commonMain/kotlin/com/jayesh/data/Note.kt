package com.jayesh.data

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jayesh.getCurrnetLocalDateTime
import com.jayesh.ui.theme.option.AppThemeOption
import com.jayesh.ui.theme.scheme.ColorDefault
import kotlinx.datetime.*

@Entity(tableName = "notes")
@Stable
data class Note(
    @PrimaryKey
    val id: String,            // Unique identifier for the note
    val title: String,         // Title of the note
    val content: String,       // Content/body of the note
    val createdAt: String,  // Date and time when the note was created
    val updatedAt: String,  // Date and time when the note was last updated
    val backgroundColor: Long = 0xffe5dcc5,
    val appThemeOption: AppThemeOption = AppThemeOption.Default,
    val isSynced: Boolean = false  // Flag to check if the note is synced with the server
)