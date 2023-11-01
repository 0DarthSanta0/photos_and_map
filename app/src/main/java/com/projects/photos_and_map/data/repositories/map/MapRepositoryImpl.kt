package com.projects.photos_and_map.data.repositories.map

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.PhotosAndMapApplication
import com.projects.photos_and_map.data.database.photos.models.PhotoEntity
import com.projects.photos_and_map.domain.repositories.MapRepository
import com.projects.photos_and_map.models.Mark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MapRepositoryImpl : MapRepository {
    override suspend fun getMarks(): Flow<Result<List<Mark>, AppErrors>> = flow {
        try {
            val marks = PhotosAndMapApplication.getPhotoDao()
                .selectAllPhotos()
            emit(
                Ok(marks.map(PhotoEntity::toMark))
            )
        } catch (e: Exception) {
            emit(
                Err(AppErrors.DataBaseError)
            )
        }
    }
}
