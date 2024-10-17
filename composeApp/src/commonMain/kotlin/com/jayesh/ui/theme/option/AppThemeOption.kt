package com.jayesh.ui.theme.option

import androidx.compose.material3.ColorScheme
import com.jayesh.ui.theme.scheme.ColorDefault

enum class AppThemeOption {
    Default, Red, Blue, Green, Yellow
}

sealed class NoteThemeOption {
    abstract val darkScheme: ColorScheme
    abstract val lighScheme: ColorScheme
    data object Default : NoteThemeOption() {
        override val darkScheme: ColorScheme
            get() = ColorDefault.darkScheme
        override val lighScheme: ColorScheme
            get() = ColorDefault.lightScheme
    }
}