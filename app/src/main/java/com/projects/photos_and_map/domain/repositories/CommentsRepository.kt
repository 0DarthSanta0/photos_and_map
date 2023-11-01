package com.projects.photos_and_map.domain.repositories

import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.models.CommentsForList
import com.projects.photos_and_map.models.Image
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    suspend fun postComment(
        text: String,
        imageId: Int
    ): Flow<Result<Unit, AppErrors>>

    suspend fun getComments(
        imageId: Int,
        page: Int
    ): Flow<Result<CommentsForList, AppErrors>>

    suspend fun getImage(
        imageId: Int
    ): Flow<Result<Image, AppErrors>>

    suspend fun deleteComment(
        imageId: Int,
        commentId: Int
    ): Flow<Result<Unit, AppErrors>>
}