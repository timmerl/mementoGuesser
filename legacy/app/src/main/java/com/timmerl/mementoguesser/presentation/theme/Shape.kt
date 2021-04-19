package com.timmerl.mementoguesser.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

const val smallRoundedCornerAsPercent = 50
val mediumRoundedCornerAsDp = 20.dp
val largeRoundedCornerAsDp = 0.dp

val Shapes = Shapes(
    small = RoundedCornerShape(percent = smallRoundedCornerAsPercent),
    medium = RoundedCornerShape(mediumRoundedCornerAsDp),
    large = RoundedCornerShape(largeRoundedCornerAsDp)
)

