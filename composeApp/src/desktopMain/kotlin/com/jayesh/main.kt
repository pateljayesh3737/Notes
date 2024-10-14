package com.jayesh

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jayesh.db.getDatabaseBuilder
import com.jayesh.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes",
    ) {
        val noteDatabaseBuilder = remember { getDatabaseBuilder() }
        App(noteDatabaseBuilder)
    }
}