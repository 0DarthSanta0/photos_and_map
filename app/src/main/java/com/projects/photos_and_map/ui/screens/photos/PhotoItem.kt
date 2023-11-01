package com.projects.photos_and_map.ui.screens.photos

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.projects.photos_and_map.R
import com.projects.photos_and_map.models.Image
import com.projects.photos_and_map.ui.screens.core.components.ConfirmationDialog
import com.projects.photos_and_map.ui.theme.AppTheme
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoItem(
    image: Image,
    onPhotoClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onPhotoLongClick: (Int) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(AppTheme.dimens.spacing10)
            .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(
                onClick = {
                    onPhotoClick(image.id)
                },
                onLongClick = {
                    showDialog = true
                }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConfirmationDialog(
            showDialog = showDialog,
            onConfirm = {
                onPhotoLongClick(image.id)
            },
            onDismiss = { showDialog = false }
        )
        Box(
            modifier = Modifier.size(AppTheme.dimens.spacing80),
            contentAlignment = Alignment.Center
        ) {
            val painter = painterResource(id = R.drawable.default_image)
            AsyncImage(
                onError = {
                    isLoading = false
                },
                onSuccess = {
                    isLoading = false
                },
                error = painter,
                model = image.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                .format(Date(image.date * 1000)).toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = AppTheme.dimens.spacing10),
        )
    }
}