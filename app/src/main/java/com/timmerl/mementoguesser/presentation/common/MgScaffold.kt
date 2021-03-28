package com.timmerl.mementoguesser.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
        Scaffold(
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
@Preview
fun MgScaffoldPreview() {
    MgScaffold(
        onAddMementoClicked = { },
        onGuesserClicked = {},
        onManagementClicked = {}
    ) {}
}
