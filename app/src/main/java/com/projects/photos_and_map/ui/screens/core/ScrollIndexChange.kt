package com.projects.photos_and_map.ui.screens.core

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun ScrollIndexChange(
    lazyGridState: LazyGridState,
    onScrollIndexChange: (Int) -> Unit
) {
    val index by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(index) {
        onScrollIndexChange(index)
    }
}
