package com.timmerl.mementoguesser.presentation

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.timmerl.mementoguesser.R

val lightTheme = Colors(
    primary = Color(R.color.colorPrimary),
    primaryVariant = Color(R.color.colorPrimaryDark),
    secondary = Color(R.color.colorSecondary),
    secondaryVariant = Color(R.color.colorSecondaryDark),
    background = Color(R.color.background),
    surface = Color(R.color.background),
    error = Color(R.color.design_default_color_error),
    onPrimary = Color(R.color.colorPrimaryDark),
    onSecondary = Color(R.color.colorSecondaryDark),
    onBackground = Color(R.color.backgroundDark),
    onSurface = Color(R.color.backgroundDark),
    onError = Color(R.color.design_default_color_error),
    isLight = true
)

val questionColor = Color(R.color.question)
val answerColor = Color(R.color.answer)

val playableColor = Color(R.color.mementoItemBackgroundPlayable)
val nonPlayableColor = Color(R.color.mementoItemBackgroundNonPlayable)