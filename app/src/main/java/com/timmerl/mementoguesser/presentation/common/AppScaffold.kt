package com.timmerl.mementoguesser.presentation.common

import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.utils.NavigationViewModel

@Composable
fun AppScaffold(
    navigationViewModel: NavigationViewModel,
    content: @Composable () -> Unit
) {
    MaterialTheme(lightTheme) {
        Scaffold(
            floatingActionButton = {},
            floatingActionButtonPosition = FabPosition.End,
        ) {}
    }
}