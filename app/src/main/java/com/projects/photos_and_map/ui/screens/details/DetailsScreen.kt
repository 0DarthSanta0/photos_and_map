package com.projects.photos_and_map.ui.screens.details

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.projects.photos_and_map.R
import com.projects.photos_and_map.ui.screens.core.components.ScrollListIndexChange
import com.projects.photos_and_map.ui.theme.AppTheme
import java.util.Date
import java.util.Locale


const val DATE_PATTERN = "dd.MM.yyyy HH:mm"

@Composable
fun DetailsScreen(
    id: Int?,
    viewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory),
    onNavigateBack: () -> Unit
) {
    val isFirstCommentsRequested = remember {
        mutableStateOf(false)
    }
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isImageLoading by viewModel.isImageLoading.collectAsStateWithLifecycle()
    val isFirstLoading by viewModel.isFirstLoading.collectAsStateWithLifecycle()
    val comments by viewModel.commentsForDisplay.collectAsStateWithLifecycle()
    val image by viewModel.image.collectAsStateWithLifecycle()
    val newCommentText by viewModel.newCommentText.collectAsStateWithLifecycle()
    val isCommentValid by viewModel.isCommentValid.collectAsStateWithLifecycle()
    val lazyListState: LazyListState = rememberLazyListState()

    if (id != null && !isFirstCommentsRequested.value) {
        viewModel.requestComments(offset = 0, imageId = id)
        viewModel.getImage(id)
        isFirstCommentsRequested.value = true
    }

    if (id != null) {
        ScrollListIndexChange(
            lazyListState = lazyListState,
            id = id,
            onScrollIndexChange = viewModel::onUIScrollIndexChange
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.spacing60)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
        }
        if (isImageLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(AppTheme.dimens.spacing80)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                var isLoadingPic by remember { mutableStateOf(true) }
                val painter = painterResource(id = R.drawable.default_image)
                AsyncImage(
                    onError = {
                        isLoadingPic = false
                    },
                    onSuccess = {
                        isLoadingPic = false
                    },
                    error = painter,
                    model = image?.url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(AppTheme.dimens.spacing08))
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(AppTheme.dimens.spacing30)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
                        .format(Date(image?.date?.times(1000) ?: 0)).toString(),
                    color = Color.White
                )
            }
        }
        CommentsField(
            id = id,
            comments = comments,
            lazyListState = lazyListState,
            isFirstLoading = isFirstLoading,
            isLoading = isLoading,
            deleteComment = viewModel::deleteComment,
            modifier = Modifier.weight(1f)
        )
        CommentInput(
            newCommentText = newCommentText,
            isCommentValid = isCommentValid,
            onCommentValueChange = viewModel::onCommentValueChange,
            postComment = viewModel::postComment,
            id = id,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
