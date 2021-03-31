package com.timmerl.mementoguesser.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.timmerl.mementoguesser.presentation.utils.LocalSysUiController


@Composable
fun MgTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) DarkColors else LightColors

    val sysUiController = LocalSysUiController.current
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.background.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvideMementoGuesserColors(colors) {
        MaterialTheme(
            colors = colors.toMaterial(),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
fun ProvideMementoGuesserColors(
    colors: MementoGuesserColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalMgColors provides colorPalette, content = content)
}

private val LocalMgColors = staticCompositionLocalOf<MementoGuesserColors> {
    error("No ColorPalette provided")
}


object MementoGuesserTheme {
    val colors: MementoGuesserColors
        @Composable
        get() = LocalMgColors.current
}

fun MementoGuesserColors.toMaterial() = Colors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError,
    isLight = !isDark
)