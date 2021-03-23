package com.timmerl.mementoguesser.presentation

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.timmerl.mementoguesser.R

val lightTheme = Colors(
    primary = Color(R.color.colorPrimary),
    primaryVariant = Color(R.color.colorPrimaryVariant),
    secondary = Color(R.color.colorSecondary),
    secondaryVariant = Color(R.color.colorSecondaryVariant),
    background = Color(R.color.background),
    surface = Color(R.color.surface),
    error = Color(R.color.design_default_color_error),
    onPrimary = Color(R.color.onPrimary),
    onSecondary = Color(R.color.onSecondary),
    onBackground = Color(R.color.onBackground),
    onSurface = Color(R.color.onSurface),
    onError = Color(R.color.design_default_color_error),
    isLight = true
)

val questionColor = Color(R.color.question)
val answerColor = Color(R.color.answer)

val playableColor = Color(R.color.mementoItemBackgroundPlayable)
val nonPlayableColor = Color(R.color.mementoItemBackgroundNonPlayable)