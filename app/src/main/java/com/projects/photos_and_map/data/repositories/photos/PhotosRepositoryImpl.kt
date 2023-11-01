package com.projects.photos_and_map.data.repositories.photos

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.PhotosAndMapApplication
import com.projects.photos_and_map.data.database.photos.models.PhotoEntity
import com.projects.photos_and_map.domain.repositories.PhotosRepository
import com.projects.photos_and_map.models.ImageDtoIn
import com.projects.photos_and_map.models.ImageDtoOut
import com.projects.photos_and_map.models.ImagesForGrid
import com.projects.photos_and_map.network.MainNetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.time.Instant


private const val LIMIT = 20
private const val CODE_200 = 200

class PhotosRepositoryImpl(
    private val retrofitAPI: MainNetworkService = MainNetworkService.getInstance(),
) : PhotosRepository {
    override suspend fun postPhoto(
        base64Image: String,
        lat: Double,
        lng: Double
    ): Flow<Result<Unit, AppErrors>> = flow {
        try {
            val post = retrofitAPI.postImage(
                body = ImageDtoIn(
                    base64Image = base64Image,
                    date = Instant.now().epochSecond,
                    lat = lat,
                    lng = lng
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

    override suspend fun getPhotos(page: Int): Flow<Result<ImagesForGrid, AppErrors>> = flow {
        try {
            val photos = retrofitAPI.getImages(page)
            emit(
                if (photos.data != null) {
                    PhotosAndMapApplication.getPhotoDao()
                        .insertListOfPhotos(photos.data.map(ImageDtoOut::toPhotoEntity))
                    val imagesForGrid = ImagesForGrid(
                        isOver = photos.data.size != LIMIT,
                        images = PhotosAndMapApplication.getPhotoDao()
                            .selectPhotos((page + 1) * LIMIT).map(PhotoEntity::toImage)
                    )
                    Ok(imagesForGrid)
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

    override suspend fun deletePhoto(imageId: Int): Flow<Result<Unit, AppErrors>> = flow {
        try {
            val delete = retrofitAPI.deleteImage(imageId)
            emit(
                if (delete.status == CODE_200) {
                    val photo = PhotosAndMapApplication.getPhotoDao().selectPhoto(imageId)
                    PhotosAndMapApplication.getPhotoDao().deleteImage(photo)
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
