package com.timmerl.mementoguesser.presentation

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val appTheme = Colors(
    primary = AppColors.blue,
    primaryVariant = AppColors.blueLight,
    secondary = AppColors.brown,
    secondaryVariant = AppColors.sand,
    background = AppColors.pale,
    surface = AppColors.pale,
    error = AppColors.error,
    onPrimary = AppColors.lightGrey,
    onSecondary = AppColors.sandLight,
    onBackground = AppColors.darkBrown,
    onSurface = AppColors.darkBrown,
    onError = AppColors.lightGrey,
    isLight = true,
)

data class GuesserCardColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val labelColor: Color,
)

val questionColorSet: List<GuesserCardColors> = listOf(
    GuesserCardColors(
        backgroundColor = AppColors.blue,
        contentColor = AppColors.lightGrey,
        labelColor = AppColors.sandLight
    ),
    GuesserCardColors(
        backgroundColor = AppColors.blueLight,
        contentColor = AppColors.lightGrey,
        labelColor = AppColors.lightGrey
    )
)

val answerCoorsSet: List<GuesserCardColors> = listOf(
    GuesserCardColors(
        backgroundColor = AppColors.brown,
        contentColor = AppColors.lightGrey,
        labelColor = AppColors.sandLight
    ),
    GuesserCardColors(
        backgroundColor = AppColors.sand,
        contentColor = AppColors.darkBrown,
        labelColor = AppColors.darkBrown
    )
)

val nonPlayableQuestionTheme = Colors(
    primary = Color(red = 0xB0, green = 0x00, blue = 0x20),
    primaryVariant = Color(red = 0x63, green = 0xa4, blue = 0xff),
    onPrimary = Color(red = 0xcf, green = 0xd8, blue = 0xdc),
    secondary = Color(red = 0x8d, green = 0x6e, blue = 0x63),
    secondaryVariant = Color(red = 0xbe, green = 0x9c, blue = 0x91),
    onSecondary = Color(red = 0xbc, green = 0xaa, blue = 0xa4),
    background = Color(red = 0xB0, green = 0x00, blue = 0x20),
    onBackground = Color(red = 0xcf, green = 0xd8, blue = 0xdc),
    surface = Color(red = 0xB0, green = 0x00, blue = 0x20),
    onSurface = Color(red = 0xcf, green = 0xd8, blue = 0xdc),
    error = Color(red = 0xB0, green = 0x00, blue = 0x20),
    onError = Color(red = 0xB0, green = 0x00, blue = 0x20),
    isLight = true
)

object AppColors {
    val blue = Color(0x19, green = 0x76, blue = 0xd2)
    val blueLight = Color(red = 0x63, green = 0xa4, blue = 0xff)
    val lightGrey = Color(red = 0xcf, green = 0xd8, blue = 0xdc)
    val brown = Color(red = 0x8d, green = 0x6e, blue = 0x63)
    val sand = Color(red = 0xbe, green = 0x9c, blue = 0x91)
    val sandLight = Color(red = 0xbc, green = 0xaa, blue = 0xa4)
    val pale = Color(red = 0xcf, green = 0xd8, blue = 0xdc)
    val error = Color(red = 0xB0, green = 0x00, blue = 0x20)
    val darkBrown = Color(red = 0x5f, green = 0x43, blue = 0x39)
}