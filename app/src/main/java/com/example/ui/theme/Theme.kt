package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LearnicleColorScheme = darkColorScheme(
    primary = CrimsonAccent,
    onPrimary = TextPrimary,
    primaryContainer = CrimsonContainer,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = TextPrimary,
    secondaryContainer = CardElevatedHover,
    onSecondaryContainer = TextPrimary,
    tertiary = AccentGold,
    onTertiary = ObsidianBackground,
    tertiaryContainer = AccentGoldContainer,
    onTertiaryContainer = AccentGold,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = CardElevated,
    onSurface = TextPrimary,
    surfaceVariant = CardElevatedHover,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    outlineVariant = BorderSubtle,
    error = CrimsonAccent,
    onError = TextPrimary
)

@Composable
fun LearnicleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LearnicleColorScheme,
        typography = Typography,
        content = content
    )
}
