package com.projects.photos_and_map.domain.repositories

import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.models.ImagesForGrid
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun postPhoto(
        base64Image: String,
        lat: Double,
        lng: Double
    ): Flow<Result<Unit, AppErrors>>

    suspend fun getPhotos(
        page: Int
    ): Flow<Result<ImagesForGrid, AppErrors>>

    suspend fun deletePhoto(
        imageId: Int
    ): Flow<Result<Unit, AppErrors>>
}