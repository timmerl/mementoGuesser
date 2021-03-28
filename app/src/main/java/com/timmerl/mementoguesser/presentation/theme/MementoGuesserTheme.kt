package com.timmerl.mementoguesser.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.timmerl.mementoguesser.presentation.utils.LocalSysUiController

private val LightColors = MementoGuesserColors(
    primary = blue,
    primaryVariant = blueLight,
    secondary = brown,
    secondaryVariant = sand,
    background = lightGrey,
    surface = lightGrey,
    error = appError,
    onPrimary = lightGrey,
    onSecondary = sandLight,
    onBackground = darkBrown,
    onSurface = darkBrown,
    onError = lightGrey,
    questionBackground = blue,
    questionContent = sandLight,
    questionLabel = sandLight,
    answerBackground = brown,
    answerContent = sandLight,
    answerLabel = sandLight,
    surfaceNotAvailable = appError,
    OnSurfaceNotAvailable = Color.White,
    isDark = false
)

private val DarkColors = MementoGuesserColors(
    primary = blue,
    primaryVariant = blueLight,
    secondary = brown,
    secondaryVariant = sand,
    background = lightGrey,
    surface = lightGrey,
    error = appError,
    onPrimary = lightGrey,
    onSecondary = sandLight,
    onBackground = darkBrown,
    onSurface = darkBrown,
    onError = lightGrey,
    questionBackground = blue,
    questionContent = sandLight,
    questionLabel = sandLight,
    answerBackground = brown,
    answerContent = sandLight,
    answerLabel = sandLight,
    surfaceNotAvailable = appError,
    OnSurfaceNotAvailable = Color.White,
    isDark = false
)


@Composable
fun MementoGuesserTheme(
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
            colors = debugColors(isDarkTheme),
            content = content,
            typography = Typography,
            shapes = Shapes,
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
    CompositionLocalProvider(LocalAppColors provides colorPalette, content = content)
}

private val LocalAppColors = staticCompositionLocalOf<MementoGuesserColors> {
    error("No ColorPalette provided")
}


object MementoGuesserTheme {
    val colors: MementoGuesserColors
        @Composable
        get() = LocalAppColors.current
}

@Stable
class MementoGuesserColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color,
    questionBackground: Color,
    questionContent: Color,
    questionLabel: Color,
    answerBackground: Color,
    answerContent: Color,
    answerLabel: Color,
    surfaceNotAvailable: Color,
    OnSurfaceNotAvailable: Color,
    isDark: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var secondaryVariant by mutableStateOf(secondaryVariant)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var error by mutableStateOf(error)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var onError by mutableStateOf(onError)
        private set
    var questionBackground by mutableStateOf(questionBackground)
        private set
    var questionContent by mutableStateOf(questionContent)
        private set
    var questionLabel by mutableStateOf(questionLabel)
        private set
    var answerBackground by mutableStateOf(answerBackground)
        private set
    var answerContent by mutableStateOf(answerContent)
        private set
    var answerLabel by mutableStateOf(answerLabel)
        private set
    var surfaceNotAvailable by mutableStateOf(surfaceNotAvailable)
        private set
    var OnSurfaceNotAvailable by mutableStateOf(OnSurfaceNotAvailable)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: MementoGuesserColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        secondaryVariant = other.secondaryVariant
        background = other.background
        surface = other.surface
        error = other.error
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        onError = other.onError
        questionBackground = other.questionBackground
        questionContent = other.questionContent
        questionLabel = other.questionLabel
        answerBackground = other.answerBackground
        answerContent = other.answerContent
        answerLabel = other.answerLabel
        surfaceNotAvailable = other.surfaceNotAvailable
        OnSurfaceNotAvailable = other.OnSurfaceNotAvailable
        isDark = other.isDark
    }
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Green
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)