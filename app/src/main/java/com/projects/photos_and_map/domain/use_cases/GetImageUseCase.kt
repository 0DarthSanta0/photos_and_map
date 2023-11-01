package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.CommentsRepository

class GetImageUseCase(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(imageId: Int) = commentsRepository.getImage(imageId = imageId)
}