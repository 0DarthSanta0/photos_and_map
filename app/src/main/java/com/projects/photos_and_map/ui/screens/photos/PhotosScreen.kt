package com.projects.photos_and_map.ui.screens.photos

import android.os.Environment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.projects.photos_and_map.R
import com.projects.photos_and_map.ui.screens.core.components.ScrollGridIndexChange
import com.projects.photos_and_map.ui.theme.AppTheme
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = viewModel(factory = PhotosViewModel.Factory),
    onItemSelect: (Int) -> Unit
) {
    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val outputDirectory: File =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    val isCameraVisible by viewModel.isCameraVisible.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isFirstLoading by viewModel.isFirstLoading.collectAsStateWithLifecycle()
    val photos by viewModel.photosForDisplay.collectAsStateWithLifecycle()
    val lazyGridState: LazyGridState = rememberLazyGridState()
    val permissions = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionState = rememberMultiplePermissionsState(permissions)

    ScrollGridIndexChange(
        lazyGridState = lazyGridState,
        onScrollIndexChange = viewModel::onUIScrollIndexChange
    )

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppTheme.dimens.spacing60)
    ) {
        if (isFirstLoading && isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(AppTheme.dimens.spacing80)
                    .align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(AppTheme.dimens.spacing08)
            ) {
                items(photos) { image ->
                    PhotoItem(
                        image = image,
                        onPhotoClick = onItemSelect,
                        onPhotoLongClick = viewModel::deleteImage
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
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (permissionState.allPermissionsGranted) {
                    viewModel.onAddNewPhoto()
                } else {
                    permissionState.launchMultiplePermissionRequest()
                    if (permissionState.allPermissionsGranted) viewModel.onAddNewPhoto()
                }
            },
            modifier = Modifier
                .padding(AppTheme.dimens.spacing15)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add))
        }
    }
    if (isCameraVisible) {
        CameraView(
            outputDirectory = outputDirectory,
            executor = cameraExecutor,
            onImageCaptured = viewModel::onPhoto,
            takePhoto = viewModel::takePhoto
        )
    }
}
