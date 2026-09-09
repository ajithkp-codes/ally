package com.ally.care.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AllyGreen = Color(0xFF2E7D6B)
private val AllyGreenDark = Color(0xFF1B5E4F)
private val AllyAmber = Color(0xFFE8A33D)
private val AllyBackground = Color(0xFFF6FAF9)

private val LightColors = lightColorScheme(
    primary = AllyGreen,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCFE9E1),
    secondary = AllyAmber,
    background = AllyBackground,
    surface = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7FCBB6),
    onPrimary = AllyGreenDark,
    secondary = AllyAmber,
    background = Color(0xFF10201C),
    surface = Color(0xFF16302A)
)

@Composable
fun AllyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = AllyTypography,
        content = content
    )
}
