package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.CommentsRepository

class GetCommentsUseCase(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(
        page: Int,
        imageId: Int
    ) = commentsRepository.getComments(
        page = page,
        imageId = imageId
    )
}
