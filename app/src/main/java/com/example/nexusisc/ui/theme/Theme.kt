package com.example.nexusisc.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ItszGreen,
    secondary = ItszGold,
    tertiary = ItszLightGreen
)

@Composable
fun NexusISCTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}