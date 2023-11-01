package com.projects.photos_and_map.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.michaelbull.result.onSuccess
import com.projects.photos_and_map.data.repositories.comments.CommentsRepositoryImpl
import com.projects.photos_and_map.domain.use_cases.DeleteCommentUseCase
import com.projects.photos_and_map.domain.use_cases.GetCommentsUseCase
import com.projects.photos_and_map.domain.use_cases.GetImageUseCase
import com.projects.photos_and_map.domain.use_cases.PostCommentUseCase
import com.projects.photos_and_map.models.Comment
import com.projects.photos_and_map.models.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


private const val LIMIT = 20
private const val INDEX_OFFSET = 6

class DetailsViewModel(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val postCommentsUseCase: PostCommentUseCase,
    private val getImageUseCase: GetImageUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
) : ViewModel() {
    private var page = 0
    private var isMoreComments = true
    private val _commentsForDisplay: MutableStateFlow<List<Comment>> = MutableStateFlow(listOf())
    val commentsForDisplay: StateFlow<List<Comment>> get() = _commentsForDisplay
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _isImageLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isImageLoading: StateFlow<Boolean> get() = _isLoading
    private val _isFirstLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isFirstLoading: StateFlow<Boolean> get() = _isFirstLoading
    private val _newCommentText: MutableStateFlow<String> = MutableStateFlow("")
    val newCommentText: StateFlow<String> get() = _newCommentText
    private val _isCommentValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCommentValid: StateFlow<Boolean> get() = _isCommentValid
    private val _image: MutableStateFlow<Image?> = MutableStateFlow(null)
    val image: StateFlow<Image?> get() = _image

    fun requestComments(offset: Int, imageId: Int) {
        viewModelScope.launch {
            getCommentsUseCase(page = offset, imageId = imageId).collect { getCommentsRequest ->
                getCommentsRequest.onSuccess { commentsForList ->
                    _commentsForDisplay.value = commentsForList.comments
                    if (commentsForList.isOver) {
                        isMoreComments = false
                    } else {
                        isMoreComments = true
                        page++
                    }
                    _isLoading.value = false
                }
            }
        }
    }

    fun postComment(imageId: Int?) {
        _isCommentValid.value = false
        viewModelScope.launch {
            if (imageId != null) {
                postCommentsUseCase(
                    imageId = imageId,
                    text = _newCommentText.value
                ).collect { postCommentRequest ->
                    postCommentRequest.onSuccess { _ ->
                        _isLoading.value = true
                        _isFirstLoading.value = true
                        requestComments(offset = page, imageId = imageId)
                        _newCommentText.value = ""
                    }
                }
            }
        }
    }

    fun deleteComment(imageId: Int?, commentId: Int) {
        viewModelScope.launch {
            if (imageId != null) {
                deleteCommentUseCase(
                    imageId = imageId,
                    commentId = commentId
                ).collect { deleteCommentRequest ->
                    deleteCommentRequest.onSuccess {
                        _commentsForDisplay.value =
                            _commentsForDisplay.value.filter { it.id != commentId }
                    }
                }
            }
        }
    }

    fun getImage(imageId: Int) {
        viewModelScope.launch {
            getImageUseCase(imageId = imageId).collect { getImageRequest ->
                getImageRequest.onSuccess { image ->
                    _image.value = image
                    _isImageLoading.value = false
                }
            }
        }
    }

    fun onUIScrollIndexChange(firstVisibleItemIndex: Int, imageId: Int) {
        if (firstVisibleItemIndex > (page) * LIMIT - (LIMIT - INDEX_OFFSET) && firstVisibleItemIndex > INDEX_OFFSET && isMoreComments) {
            _isLoading.value = true
            _isFirstLoading.value = false
            requestComments(
                offset = page,
                imageId = imageId
            )
        }
    }

    fun onCommentValueChange(newValue: String) {
        _isCommentValid.value = newValue.isNotEmpty()
        _newCommentText.value = newValue
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val commentsRepository = CommentsRepositoryImpl()
                DetailsViewModel(
                    postCommentsUseCase = PostCommentUseCase(commentsRepository),
                    getCommentsUseCase = GetCommentsUseCase(commentsRepository),
                    getImageUseCase = GetImageUseCase(commentsRepository),
                    deleteCommentUseCase = DeleteCommentUseCase(commentsRepository)
                )
            }
        }
    }
}
