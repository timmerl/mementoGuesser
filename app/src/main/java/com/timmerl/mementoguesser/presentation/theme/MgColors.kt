package com.timmerl.mementoguesser.presentation.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

data class MementoColors(
    val questionBackground: List<Color>,
    val question: List<Color>,
    val answerBackground: List<Color>,
    val answer: List<Color>,
    val questionContent: List<Color>,
    val answerContent: Color
)

internal val LightColors = MementoGuesserColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryDarkColor,
    secondary = SecondaryColor,
    secondaryVariant = SecondaryDarkColor,
    background = SecondaryLightColor,
    surface = Ocean2,
    error = FunctionalRed,
    onPrimary = PrimaryTextColor,
    onSecondary = SecondaryTextColor,
    onBackground = SecondaryTextColor,
    onSurface = Neutral6,
    onError = FunctionalGrey,
    surfaceNotAvailable = FunctionalRed,
    onSurfaceNotAvailable = FunctionalGrey,
    mementoColors = MementoColors(
        questionBackground = listOf(Ocean9),
        questionContent = listOf(Ocean2),
        question = listOf(Ocean1),
        answerBackground = listOf(Ocean11),
        answerContent = Ocean3,
        answer = listOf(Ocean2)
    ),
    isDark = false
)

internal val DarkColors = MementoGuesserColors(
    primary = DarkPrimaryColor,
    primaryVariant = DarkPrimaryDarkColor,
    secondary = DarkSecondaryColor,
    secondaryVariant = DarkSecondaryDarkColor,
    background = DarkPrimaryLightColor,
    surface = DarkSecondaryLightColor,
    error = FunctionalRed,
    onPrimary = DarkPrimaryTextColor,
    onSecondary = DarkSecondaryTextColor,
    onBackground = DarkPrimaryTextColor,
    onSurface = DarkSecondaryTextColor,
    onError = FunctionalGrey,
    surfaceNotAvailable = FunctionalRed,
    onSurfaceNotAvailable = FunctionalGrey,
    mementoColors = MementoColors(
        questionBackground = listOf(Cyan800),
        questionContent = listOf(Cyan200),
        question = listOf(Cyan50),
        answerBackground = listOf(Sky800),
        answerContent = Sky200,
        answer = listOf(Sky50)
    ),
    isDark = true
)

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
    mementoColors: MementoColors,
    surfaceNotAvailable: Color,
    onSurfaceNotAvailable: Color,
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
    var guesserColors by mutableStateOf(mementoColors)
        private set
    var surfaceNotAvailable by mutableStateOf(surfaceNotAvailable)
        private set
    var onSurfaceNotAvailable by mutableStateOf(onSurfaceNotAvailable)
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
        guesserColors = other.guesserColors
        surfaceNotAvailable = other.surfaceNotAvailable
        onSurfaceNotAvailable = other.onSurfaceNotAvailable
        isDark = other.isDark
    }

    fun answerBackground(idx: Int): Color {
        val index = if (idx < 0) {
            guesserColors.answerBackground.size - 1
        } else idx % guesserColors.answerBackground.size
        return guesserColors.answerBackground[index]
    }

    fun questionBackground(idx: Int): Color {
        val index = if (idx < 0) {
            guesserColors.questionBackground.size - 1
        } else idx % guesserColors.questionBackground.size
        return guesserColors.questionBackground[index]
    }

    fun questionContent(idx: Int): Color {
        val index = if (idx < 0) {
            guesserColors.questionContent.size - 1
        } else idx % guesserColors.questionContent.size
        return guesserColors.questionContent[index]
    }

    fun question(idx: Int): Color {
        val index = if (idx < 0) {
            guesserColors.question.size - 1
        } else idx % guesserColors.question.size
        return guesserColors.question[index]
    }
}