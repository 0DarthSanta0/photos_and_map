package com.projects.photos_and_map.ui.screens.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.projects.photos_and_map.models.Comment
import com.projects.photos_and_map.ui.screens.core.components.ConfirmationDialog
import com.projects.photos_and_map.ui.theme.AppTheme
import java.text.DateFormat
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentItem(
    id: Int?,
    comment: Comment,
    deleteComment: (imageId: Int?, commentId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(
                onClick = { },
                onLongClick = {
                    showDialog = true
                }
            ),
    ) {
        ConfirmationDialog(
            showDialog = showDialog,
            onConfirm = {
                deleteComment(id, comment.id)
            },
            onDismiss = { showDialog = false }
        )
        Text(
            text = comment.text,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = AppTheme.dimens.spacing05)
        )
        Text(
            modifier = Modifier
                .padding(AppTheme.dimens.spacing05)
                .align(Alignment.BottomEnd),
            text = DateFormat.getDateInstance()
                .format(Date(comment.date * 1000)).toString()
        )
    }
}

