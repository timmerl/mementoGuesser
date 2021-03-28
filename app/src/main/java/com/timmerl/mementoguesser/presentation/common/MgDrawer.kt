package com.timmerl.mementoguesser.presentation.common


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@Composable
fun ColumnScope.MgDrawer(
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit
) {
    Spacer(Modifier.statusBarsHeight())
    DrawerHeader(
        onManagementClicked = onManagementClicked,
        onAddMementoClicked = onAddMementoClicked
    )
}

@Composable
fun MgDrawerScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    MgScaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            MgDrawer(
                onManagementClicked = onManagementClicked,
                onAddMementoClicked = onAddMementoClicked
            )
        },
        content = content
    )
}

@Composable
private fun DrawerHeader(
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onManagementClicked)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onAddMementoClicked)
        )
    }
}

@Composable
@Preview
fun DrawerPreview() {
    MgTheme {
        MgSurface {
            Column {
                MgDrawer({}, {})
            }
        }
    }
}

@Composable
@Preview
fun DrawerPreviewDark() {
    MgTheme(isDarkTheme = true) {
        MgSurface {
            Column {
                MgDrawer({}, {})
            }
        }
    }
}
