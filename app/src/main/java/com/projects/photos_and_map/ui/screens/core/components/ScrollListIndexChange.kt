package com.projects.photos_and_map.ui.screens.core.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun ScrollListIndexChange(
    lazyListState: LazyListState,
    id: Int,
    onScrollIndexChange: (firstVisibleItemIndex: Int, imageId: Int) -> Unit
) {
    val index by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }
    LaunchedEffect(index) {
        onScrollIndexChange(index, id)
    }
}
