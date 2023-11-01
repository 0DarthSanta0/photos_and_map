package com.projects.photos_and_map.ui.screens.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.projects.photos_and_map.R
import com.projects.photos_and_map.ui.theme.AppTheme

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            Column(
                modifier = Modifier
                    .padding(AppTheme.dimens.spacing15)
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.confirm_question),
                    modifier = Modifier.padding(top = AppTheme.dimens.spacing15)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.padding(AppTheme.dimens.spacing08)
                    ) {
                        Text(stringResource(R.string.cancel_button))
                    }
                    TextButton(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        modifier = Modifier.padding(AppTheme.dimens.spacing08)
                    ) {
                        Text(stringResource(R.string.confirm_button))
                    }
                }
            }
        }
    }
}
