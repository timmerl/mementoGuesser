package com.timmerl.mementoguesser.presentation.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme

@Composable
fun MgScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    MementoGuesserTheme {
        Scaffold(
            drawerBackgroundColor = MementoGuesserTheme.colors.background,
            drawerContentColor = contentColorFor(backgroundColor = MementoGuesserTheme.colors.background),
            scaffoldState = scaffoldState,
            drawerContent = drawerContent,
            content = content
        )
    }
}
