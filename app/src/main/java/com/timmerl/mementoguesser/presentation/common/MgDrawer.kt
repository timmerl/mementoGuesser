package com.timmerl.mementoguesser.presentation.common


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme

@Composable
fun ColumnScope.MgDrawer(
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit
) {
    // Use statusBarsHeight() to add a spacer which pushes the drawer content
    // below the status bar (y-axis)
    Spacer(Modifier.statusBarsHeight())
    DrawerHeader()
    Divider()
    DrawerItemHeader("Chats")
    ChatItem("composers", true) { onManagementClicked() }
    ChatItem("droidcon-nyc", false) { onManagementClicked() }
    DrawerItemHeader("Recent Profiles")
    ProfileItem("Ali Conors (you)") { onAddMementoClicked() }
}

@Composable
fun MgDrawerScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    MementoGuesserTheme {
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
}

@Composable
private fun DrawerHeader() {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun DrawerItemHeader(text: String) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(text, style = MaterialTheme.typography.caption, modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun ChatItem(text: String, selected: Boolean, onChatClicked: () -> Unit) {
    val background = if (selected) {
        Modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.08f))
    } else {
        Modifier
    }
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(background)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onChatClicked),
        verticalAlignment = CenterVertically
    ) {
        val iconTint = if (selected) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_refresh),
            tint = iconTint,
            modifier = Modifier.padding(8.dp),
            contentDescription = null
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text,
                style = MaterialTheme.typography.body2,
                color = if (selected) MaterialTheme.colors.primary else LocalContentColor.current,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun ProfileItem(text: String, onProfileClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onProfileClicked),
        verticalAlignment = CenterVertically
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val widthPaddingModifier = Modifier
                .padding(8.dp)
                .size(24.dp)
            Spacer(modifier = widthPaddingModifier)
            Text(text, style = MaterialTheme.typography.body2, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
@Preview
fun DrawerPreview() {
    MementoGuesserTheme {
        Surface {
            Column {
                MgDrawer({}, {})
            }
        }
    }
}

@Composable
@Preview
fun DrawerPreviewDark() {
    MementoGuesserTheme(isDarkTheme = true) {
        Surface {
            Column {
                MgDrawer({}, {})
            }
        }
    }
}
