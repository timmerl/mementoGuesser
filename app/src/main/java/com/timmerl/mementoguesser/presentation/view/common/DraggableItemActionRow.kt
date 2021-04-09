package com.timmerl.mementoguesser.presentation.view.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DraggableItemActionRowActionRow(
    actionIconSize: Dp,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onEdit,
            content = {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_edit),
//                    tint = Color.Gray,
                    contentDescription = "edit action",
                )
            }
        )
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onDelete,
            content = {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
//                    tint = Color.Gray,/
                    contentDescription = "delete action",
                )
            },
        )
    }
}