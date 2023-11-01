package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.MapRepository

class GetMarksUseCase(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke() = mapRepository.getMarks()
}
