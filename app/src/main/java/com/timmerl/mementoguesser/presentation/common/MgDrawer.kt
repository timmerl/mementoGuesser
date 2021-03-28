package com.timmerl.mementoguesser.presentation.common


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@Composable
fun ColumnScope.MgDrawer(
    onGuesserClicked: () -> Unit,
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit
) {
    Spacer(Modifier.statusBarsHeight())
    DrawerHeader(
        onGuesserClicked = onGuesserClicked,
        onManagementClicked = onManagementClicked,
        onAddMementoClicked = onAddMementoClicked
    )
}

@Composable
fun MgDrawerScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onGuesserClicked: () -> Unit,
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    MgTheme {
        MgScaffold(
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
private fun DrawerHeader(
    onGuesserClicked: () -> Unit,
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = onGuesserClicked),
            colorFilter = ColorFilter.tint(MementoGuesserTheme.colors.primary)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = onManagementClicked),
            colorFilter = ColorFilter.tint(MementoGuesserTheme.colors.secondary)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = onAddMementoClicked),
            colorFilter = ColorFilter.tint(MementoGuesserTheme.colors.primary)
        )
    }
}

@Composable
@Preview
fun DrawerPreview() {
    MgTheme {
        MgSurface {
            Column {
                MgDrawer({}, {}, {})
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
                MgDrawer({}, {}, {})
            }
        }
    }
}
