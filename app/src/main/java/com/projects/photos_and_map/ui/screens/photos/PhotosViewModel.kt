package com.projects.photos_and_map.ui.screens.photos

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.michaelbull.result.onSuccess
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import com.projects.photos_and_map.PhotosAndMapApplication
import com.projects.photos_and_map.data.repositories.photos.PhotosRepositoryImpl
import com.projects.photos_and_map.domain.use_cases.DeleteImageUseCase
import com.projects.photos_and_map.domain.use_cases.GetPhotosUseCase
import com.projects.photos_and_map.domain.use_cases.PostImageUseCase
import com.projects.photos_and_map.models.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class PhotosViewModel(
    private val postImageUseCase: PostImageUseCase,
    private val getPhotosUseCase: GetPhotosUseCase,
    private val deleteImageUseCase: DeleteImageUseCase
) : ViewModel() {
    private var page = 0
    private val _isCameraVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCameraVisible: StateFlow<Boolean> get() = _isCameraVisible
    private val _isMorePhotos: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _photosForDisplay: MutableStateFlow<List<Image>> = MutableStateFlow(listOf())
    val photosForDisplay: StateFlow<List<Image>> get() = _photosForDisplay
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _isFirstLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isFirstLoading: StateFlow<Boolean> get() = _isFirstLoading

    init {
        requestPhotos(page)
    }

    fun onAddNewPhoto() {
        _isCameraVisible.value = true
    }

    @SuppressLint("MissingPermission")
    fun onPhoto(uri: Uri) {
        _isCameraVisible.value = false
        LocationServices.getFusedLocationProviderClient(PhotosAndMapApplication.applicationContext()).lastLocation.onSuccessTask { location ->
                viewModelScope.launch {
                    val photo: String? = resizeAndEncodeImage(uri)
                    if (photo != null) {
                        postImageUseCase(
                            base64Image = photo,
                            lng = location?.longitude ?: 0.0,
                            lat = location?.latitude ?: 0.0
                        ).collect { postRequest ->
                            postRequest.onSuccess { }
                        }
                    }
                    _isLoading.value = true
                    _isFirstLoading.value = false
                    page = 0
                    requestPhotos(page)
            }
            Tasks.forResult(null)
        }
    }

    fun onUIScrollIndexChange(firstVisibleItemIndex: Int) {
        if (firstVisibleItemIndex >= (page) * 20 - 14 && firstVisibleItemIndex >= 6 && _isMorePhotos.value) {
            _isLoading.value = true
            _isFirstLoading.value = false
            requestPhotos(page)
        }
    }

    fun deleteImage(imageId: Int) {
        viewModelScope.launch {
            deleteImageUseCase(imageId).collect { deleteImageRequest ->
                deleteImageRequest.onSuccess {
                    _photosForDisplay.value = _photosForDisplay.value.filter { it.id != imageId }
                }
            }
        }
    }

    private fun requestPhotos(offset: Int) {
        viewModelScope.launch {
            getPhotosUseCase(offset).collect { getPhotosRequest ->
                getPhotosRequest.onSuccess { imagesForGrid ->
                    _photosForDisplay.value = imagesForGrid.images
                    if (imagesForGrid.isOver) {
                        _isMorePhotos.value = false
                    } else {
                        _isMorePhotos.value = true
                        page++
                    }
                    _isLoading.value = false
                }
            }
        }
    }


    private fun resizeAndEncodeImage(uri: Uri): String? {
        val bitmap = BitmapFactory.decodeFile(uri.path)
        val maxWidth = 1280
        val maxHeight = 1280
        var newWidth = bitmap.width
        var newHeight = bitmap.height

        if (bitmap.width > maxWidth || bitmap.height > maxHeight) {
            if (bitmap.width > bitmap.height) {
                newWidth = maxWidth
                newHeight = (bitmap.height * maxWidth) / bitmap.width
            } else {
                newHeight = maxHeight
                newWidth = (bitmap.width * maxHeight) / bitmap.height
            }
        }

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

        return if (base64Image.length <= 2 * 1024 * 1024) {
            base64Image
        } else {
            null
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val photosRepositoryImpl = PhotosRepositoryImpl()
                PhotosViewModel(
                    postImageUseCase = PostImageUseCase(photosRepositoryImpl),
                    getPhotosUseCase = GetPhotosUseCase(photosRepositoryImpl),
                    deleteImageUseCase = DeleteImageUseCase(photosRepositoryImpl)
                )
            }
        }
    }
}
