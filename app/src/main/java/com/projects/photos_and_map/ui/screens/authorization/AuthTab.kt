package com.projects.photos_and_map.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AuthTab(
    isSelected: Boolean,
    text: String,
    onTabSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Tab(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary),
        selected = isSelected,
        onClick = { onTabSelected() }
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}
