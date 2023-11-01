package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.PhotosRepository

class PostImageUseCase(
    private val photosRepository: PhotosRepository
) {
    suspend operator fun invoke(
        base64Image: String,
        lng: Double,
        lat: Double
    ) = photosRepository.postPhoto(
        base64Image = base64Image,
        lng = lng,
        lat = lat
    )
}