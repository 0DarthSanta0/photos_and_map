package com.projects.photos_and_map.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.projects.photos_and_map.R
import com.projects.photos_and_map.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentInput(
    newCommentText: String,
    isCommentValid: Boolean,
    onCommentValueChange: (String) -> Unit,
    postComment: (Int?) -> Unit,
    id: Int?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        OutlinedTextField(
            value = newCommentText,
            onValueChange = onCommentValueChange,
            singleLine = true,
            modifier = Modifier
                .height(AppTheme.dimens.spacing60)
                .width(AppTheme.dimens.spacing300)
                .padding(start = AppTheme.dimens.spacing08),
            placeholder = { Text(stringResource(R.string.comment)) }
        )
        IconButton(
            onClick = {
                postComment(id)
            },
            enabled = isCommentValid,
            modifier = Modifier
                .height(AppTheme.dimens.spacing40)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(R.string.send_button),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
