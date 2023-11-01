package com.projects.photos_and_map.ui.screens.details

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import com.projects.photos_and_map.ui.screens.core.components.ConfirmationDialog
import com.projects.photos_and_map.ui.theme.AppTheme
import java.text.DateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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

    val index by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(index) {
        if (id != null) {
            viewModel.onUIScrollIndexChange(firstVisibleItemIndex = index, imageId = id)
        }
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
                    contentDescription = stringResource(R.string.menu),
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
                    text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                        .format(Date(image?.date?.times(1000) ?: 0)).toString(),
                    color = Color.White
                )
            }
        }
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .weight(1f)
        ) {
            if (isFirstLoading && isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(AppTheme.dimens.spacing80)
                        .align(Alignment.Center)
                )
            } else {
                var showDialog by remember { mutableStateOf(false) }
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(AppTheme.dimens.spacing10),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing10),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(
                        items = comments,
                        key = { comments -> comments.id }
                    ) { comment ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(AppTheme.dimens.spacing40)
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
                                    viewModel.deleteComment(imageId = id, commentId = comment.id)
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
                    item {
                        if (isLoading) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .size(AppTheme.dimens.spacing20)
                                )
                            }
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                value = newCommentText,
                onValueChange = viewModel::onCommentValueChange,
                singleLine = true,
                modifier = Modifier
                    .height(AppTheme.dimens.spacing60)
                    .width(AppTheme.dimens.spacing300)
                    .padding(start = AppTheme.dimens.spacing08),
                placeholder = { Text(stringResource(R.string.comment)) }
            )
            IconButton(
                onClick = {
                    viewModel.postComment(id)
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
}