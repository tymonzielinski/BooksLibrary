package com.example.bookslibrary.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val CustomColorScheme = lightColorScheme(
    primary = ButtonColor,
    onPrimary = ButtonContent,
    background = MainBackground,
    surface = MainBackground,
    onBackground = ButtonContent,
    onSurface = ButtonContent,
    secondary = TabSelected,
    onSecondary = ButtonContent
)

@Composable
fun BooksLibraryTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = Typography,
        content = content
    )
}