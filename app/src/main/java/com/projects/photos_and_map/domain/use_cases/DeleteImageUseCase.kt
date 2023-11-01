package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.PhotosRepository

class DeleteImageUseCase(
    private val photosRepository: PhotosRepository
) {
    suspend operator fun invoke(
        imageId: Int
    ) = photosRepository.deletePhoto(
        imageId = imageId
    )
}
