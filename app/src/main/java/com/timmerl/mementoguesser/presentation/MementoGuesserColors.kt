package com.timmerl.mementoguesser.presentation

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.timmerl.mementoguesser.R

val lightTheme = Colors(
    primary = Color(red = 0x19, green = 0x76, blue = 0xd2),
    primaryVariant = Color(red = 0x63, green = 0xa4, blue = 0xff),
    onPrimary = Color(red = 0xcf, green = 0xd8, blue = 0xdc),
    secondary = Color(red = 0x8d, green = 0x6e, blue = 0x63),
    secondaryVariant = Color(red = 0xbe, green = 0x9c, blue = 0x91),
    onSecondary = Color(red = 0xbc, green = 0xaa, blue = 0xa4),
    background = Color(red = 0xcf, green = 0xd8, blue = 0xdc),
    onBackground = Color(red = 0x19, green = 0x76, blue = 0xd2),
    surface = Color(red = 0xcf, green = 0xd8, blue = 0xdc),
    onSurface = Color(red = 0x19, green = 0x76, blue = 0xd2),
    error = Color(red = 0xB0, green = 0x00, blue = 0x20),
    onError = Color(red = 0xB0, green = 0x00, blue = 0x20),
    isLight = true
)
/*
    <color name="surface">#cfd8dc</color>
    <color name="onSurface">#1976d2</color>
    <color name="background">#cfd8dc</color>
    <color name="onBackground">#1976d2</color>

 */

val questionColor = Color(R.color.question)
val answerColor = Color(R.color.answer)

val playableColor = Color(R.color.mementoItemBackgroundPlayable)
val nonPlayableColor = Color(R.color.mementoItemBackgroundNonPlayable)