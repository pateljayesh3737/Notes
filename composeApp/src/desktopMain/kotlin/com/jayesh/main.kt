package com.jayesh

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jayesh.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes",
    ) {
        App()
    }
}