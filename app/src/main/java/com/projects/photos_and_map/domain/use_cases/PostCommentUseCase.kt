package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.CommentsRepository

class PostCommentUseCase(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(
        text: String,
        imageId: Int
    ) = commentsRepository.postComment(
        text = text,
        imageId = imageId
    )
}
