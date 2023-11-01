package com.projects.photos_and_map.ui.screens.authorization

import androidx.compose.foundation.layout.height
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.projects.photos_and_map.R
import com.projects.photos_and_map.ui.theme.AppTheme

@Composable
fun Tabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTab,
        modifier = modifier,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = Color.White,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab])
            )
        }
    ) {
        AuthTab(
            isSelected = selectedTab == 0,
            text = stringResource(R.string.login),
            onTabSelected = { onTabSelected(0) },
            modifier = Modifier.height(AppTheme.dimens.spacing80)
        )
        AuthTab(
            isSelected = selectedTab == 1,
            text = stringResource(R.string.register),
            onTabSelected = { onTabSelected(1) },
            modifier = Modifier.height(AppTheme.dimens.spacing80)
        )
    }
}
