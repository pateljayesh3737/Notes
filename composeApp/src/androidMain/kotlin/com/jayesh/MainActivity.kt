package com.jayesh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jayesh.db.getDatabaseBuilder
import com.jayesh.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteDatabaseBuilder = getDatabaseBuilder(this)
        setContent {
            App(noteDatabaseBuilder = noteDatabaseBuilder)
        }
    }
}
