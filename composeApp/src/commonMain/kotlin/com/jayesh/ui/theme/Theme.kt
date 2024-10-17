package com.jayesh.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.jayesh.ui.theme.option.AppThemeOption
import com.jayesh.ui.theme.scheme.ColorDefault
import com.jayesh.ui.theme.scheme.ColorGreen
import com.jayesh.ui.theme.scheme.ColorRed
import com.jayesh.ui.theme.scheme.ColorBlue
import com.jayesh.ui.theme.scheme.ColorYellow

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeOption: AppThemeOption = AppThemeOption.Default,
    content: @Composable () -> Unit
) {
    val colorScheme = themeOption.getColorScheme(darkTheme)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

fun AppThemeOption.getColorScheme(darkTheme: Boolean): ColorScheme {
    return when (this) {
        AppThemeOption.Default -> if (darkTheme) ColorDefault.darkScheme else ColorDefault.lightScheme
        AppThemeOption.Red -> if (darkTheme) ColorRed.darkScheme else ColorRed.lightScheme
        AppThemeOption.Blue -> if (darkTheme) ColorBlue.darkScheme else ColorBlue.lightScheme
        AppThemeOption.Green -> if (darkTheme) ColorGreen.darkScheme else ColorGreen.lightScheme
        AppThemeOption.Yellow -> if (darkTheme) ColorYellow.darkScheme else ColorYellow.lightScheme
    }
}