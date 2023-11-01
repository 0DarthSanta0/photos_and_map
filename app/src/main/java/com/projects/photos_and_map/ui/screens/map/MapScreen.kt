package com.projects.photos_and_map.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.projects.photos_and_map.ui.theme.AppTheme

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel(factory = MapViewModel.Factory)
) {

    val points by viewModel.marks.collectAsStateWithLifecycle()

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = true)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppTheme.dimens.spacing60),
        uiSettings = uiSettings,
    ) {
        points.forEach { point ->
            Marker(
                position = LatLng(point.lat, point.lng),
                title = "Image (${point.lat}, ${point.lng})",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_RED
                )
            )
        }
    }
}