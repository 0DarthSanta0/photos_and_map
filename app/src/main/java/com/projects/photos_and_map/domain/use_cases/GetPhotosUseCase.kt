package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.PhotosRepository

class GetPhotosUseCase(
    private val photosRepository: PhotosRepository
) {
    suspend operator fun invoke(page: Int) = photosRepository.getPhotos(page)
}
