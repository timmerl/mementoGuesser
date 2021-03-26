package com.timmerl.mementoguesser.presentation.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.navigation.NavigationViewModel

@Composable
fun AppScaffold(navigationViewModel: NavigationViewModel) {
    MaterialTheme(lightTheme) {
        Scaffold() {

        }
    }
}