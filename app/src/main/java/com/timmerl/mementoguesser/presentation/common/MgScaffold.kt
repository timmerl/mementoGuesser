package com.timmerl.mementoguesser.presentation.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@Composable
fun MgScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onGuesserClicked: () -> Unit,
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    MgTheme {
        BaseMgScaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                MgDrawer(
                    onGuesserClicked = onGuesserClicked,
                    onManagementClicked = onManagementClicked,
                    onAddMementoClicked = onAddMementoClicked
                )
            },
            content = content
        )
    }
}

@Composable
fun BaseMgScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MementoGuesserTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MementoGuesserTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
) {
    MgTheme {
        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked,
            drawerContent = drawerContent,
            drawerGesturesEnabled = drawerGesturesEnabled,
            drawerShape = drawerShape,
            drawerElevation = drawerElevation,
            drawerBackgroundColor = drawerBackgroundColor,
            drawerContentColor = drawerContentColor,
            drawerScrimColor = drawerScrimColor,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            content = content
        )
    }
}

