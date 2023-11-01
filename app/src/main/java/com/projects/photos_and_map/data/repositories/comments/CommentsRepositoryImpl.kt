package com.projects.photos_and_map.data.repositories.comments

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.PhotosAndMapApplication
import com.projects.photos_and_map.data.database.comments.models.CommentEntity
import com.projects.photos_and_map.data.repositories.photos.toImage
import com.projects.photos_and_map.domain.repositories.CommentsRepository
import com.projects.photos_and_map.models.CommentDtoIn
import com.projects.photos_and_map.models.CommentsForList
import com.projects.photos_and_map.models.Image
import com.projects.photos_and_map.network.MainNetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


private const val LIMIT = 20
private const val CODE_200 = 200

class CommentsRepositoryImpl(
    private val retrofitAPI: MainNetworkService = MainNetworkService.getInstance(),
) : CommentsRepository {
    override suspend fun getComments(
        imageId: Int,
        page: Int
    ): Flow<Result<CommentsForList, AppErrors>> = flow {
        try {
            val comments = retrofitAPI.getComments(
                page = page,
                imageId = imageId
            )
            emit(
                if (comments.data != null) {
                    PhotosAndMapApplication.getCommentDao()
                        .insertListOfComments(comments.data.map { it.toCommentEntity(imageId) })
                    val commentsForList = CommentsForList(
                        isOver = comments.data.size != LIMIT,
                        comments = PhotosAndMapApplication.getCommentDao()
                            .selectComments(
                                limit = (page + 1) * LIMIT,
                                imageId = imageId
                            ).map(CommentEntity::toComment)
                    )
                    Ok(commentsForList)
                } else {
                    Err(AppErrors.ResponseError)
                }
            )
        } catch (e: HttpException) {
            emit(
                Err(AppErrors.ResponseError)
            )
        }
    }

    override suspend fun postComment(text: String, imageId: Int): Flow<Result<Unit, AppErrors>> =
        flow {
            try {
                val post = retrofitAPI.postComment(
                    imageId = imageId,
                    body = CommentDtoIn(
                        text = text
                    )
                )
                emit(
                    if (post.data != null) {
                        Ok(Unit)
                    } else {
                        Err(AppErrors.ResponseError)
                    }
                )
            } catch (e: HttpException) {
                emit(
                    Err(AppErrors.ResponseError)
                )
            }
        }

    override suspend fun getImage(imageId: Int): Flow<Result<Image, AppErrors>> = flow {
        try {
            val image = PhotosAndMapApplication.getPhotoDao()
                .selectPhoto(id = imageId)
            emit(
                Ok(image.toImage())
            )
        } catch (e: Exception) {
            emit(
                Err(AppErrors.DataBaseError)
            )
        }
    }

    override suspend fun deleteComment(
        imageId: Int,
        commentId: Int
    ): Flow<Result<Unit, AppErrors>> = flow {
        try {
            val delete = retrofitAPI.deleteComment(
                imageId = imageId,
                commentId = commentId
            )
            emit(
                if (delete.status == CODE_200) {
                    val comment = PhotosAndMapApplication.getCommentDao().selectComment(commentId)
                    PhotosAndMapApplication.getCommentDao().deleteComment(comment)
                    Ok(Unit)
                } else {
                    Err(AppErrors.ResponseError)
                }
            )
        } catch (e: HttpException) {
            emit(
                Err(AppErrors.ResponseError)
            )
        }
    }
}
