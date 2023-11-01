package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.CommentsRepository

class DeleteCommentUseCase(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(
        commentId: Int,
        imageId: Int
    ) = commentsRepository.deleteComment(
        commentId = commentId,
        imageId = imageId
    )
}
