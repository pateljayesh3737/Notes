package com.jayesh

import androidx.compose.ui.window.ComposeUIViewController
import com.jayesh.db.getDatabaseBuilder
import com.jayesh.ui.App

fun MainViewController() = ComposeUIViewController { App(getDatabaseBuilder()) }