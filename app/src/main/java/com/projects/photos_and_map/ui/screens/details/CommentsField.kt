package com.projects.photos_and_map.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projects.photos_and_map.models.Comment
import com.projects.photos_and_map.ui.theme.AppTheme

@Composable
fun CommentsField(
    id: Int?,
    comments: List<Comment>,
    lazyListState: LazyListState,
    isFirstLoading: Boolean,
    isLoading: Boolean,
    deleteComment: (Int?, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        if (isFirstLoading && isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(AppTheme.dimens.spacing80)
                    .align(Alignment.Center)
            )
        } else {
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
                    CommentItem(
                        id = id,
                        comment = comment,
                        deleteComment = deleteComment,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppTheme.dimens.spacing40)
                    )
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
}
