package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ImmersiveGold,
    secondary = ImmersiveGreen,
    tertiary = ImmersiveBlue,
    background = ImmersiveBg,
    surface = ImmersiveHeaderBg,
    onPrimary = ImmersiveBg,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFF8FAFC), // slate-100/50
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = ImmersiveCardMedium,
    onSurfaceVariant = ImmersiveTextSec
)

private val LightColorScheme = lightColorScheme(
    primary = ImmersiveGreen,
    secondary = ImmersiveBlue,
    tertiary = ImmersiveGold,
    background = ImmersiveBg, // Maintain immersive aesthetic in both modes
    surface = ImmersiveHeaderBg,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = ImmersiveBg,
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = ImmersiveCardLight,
    onSurfaceVariant = ImmersiveTextSec
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Enforce our custom national flag palettes
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
