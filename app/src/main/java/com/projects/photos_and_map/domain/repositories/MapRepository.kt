package com.projects.photos_and_map.domain.repositories

import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.models.Mark
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getMarks(): Flow<Result<List<Mark>, AppErrors>>
}