package com.timmerl.mementoguesser.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@Composable
fun ColumnScope.MgDrawer(
    onGuesserClicked: () -> Unit,
    onManagementClicked: () -> Unit,
    onAddMementoClicked: () -> Unit,
) {
    // Use statusBarsHeight() to add a spacer which pushes the drawer content
    // below the status bar (y-axis)
    Spacer(Modifier.statusBarsHeight())
    DrawerHeader()
    Divider()
    DrawerItemHeader("Menu")
    ProfileItem("Guesser", onProfileClicked = onGuesserClicked)
    ProfileItem("Management", onProfileClicked = onManagementClicked)
    ProfileItem("New memento", onProfileClicked = onAddMementoClicked)
}

@Composable
private fun DrawerHeader() {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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
private fun ProfileItem(text: String, onProfileClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onProfileClicked),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val widthPaddingModifier = Modifier
                .padding(8.dp)
                .size(24.dp)
            Image(
                painter = painterResource(id = R.drawable.ic_refresh),
                modifier = widthPaddingModifier.then(Modifier.clip(CircleShape)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(text, style = MaterialTheme.typography.body2, modifier = Modifier.padding(8.dp))
        }
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
        Surface {
            Column {
                MgDrawer({}, {}, {})
            }
        }
    }
}
