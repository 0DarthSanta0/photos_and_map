package com.projects.photos_and_map.network

import com.projects.photos_and_map.models.BaseResponse
import com.projects.photos_and_map.models.CommentDtoIn
import com.projects.photos_and_map.models.CommentDtoOut
import com.projects.photos_and_map.models.ImageDtoIn
import com.projects.photos_and_map.models.ImageDtoOut
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


private const val IMAGE_ID = "imageId"
private const val COMMENT_ID = "commentId"
private const val PAGE = "page"
private const val BASE_URL = "https://junior.balinasoft.com/"
private const val IMAGE_URL = "api/image"
private const val COMMENT_URL = "/api/image/{$IMAGE_ID}/comment"
private const val DELETE_IMAGE_URL = "/api/image/{$IMAGE_ID}"
private const val DELETE_COMMENT_URL = "/api/image/{$IMAGE_ID}/comment/{$COMMENT_ID}"

interface MainNetworkService {
    @POST(IMAGE_URL)
    suspend fun postImage(
        @Body body: ImageDtoIn
    ): BaseResponse<ImageDtoOut>

    @GET(IMAGE_URL)
    suspend fun getImages(
        @Query(PAGE) page: Int
    ): BaseResponse<List<ImageDtoOut>>

    @DELETE(DELETE_IMAGE_URL)
    suspend fun deleteImage(
        @Path(IMAGE_ID) imageId: Int,
    ): BaseResponse<ImageDtoOut>

    @POST(COMMENT_URL)
    suspend fun postComment(
        @Path(IMAGE_ID) imageId: Int,
        @Body body: CommentDtoIn
    ): BaseResponse<CommentDtoOut>

    @GET(COMMENT_URL)
    suspend fun getComments(
        @Path(IMAGE_ID) imageId: Int,
        @Query(PAGE) page: Int
    ): BaseResponse<List<CommentDtoOut>>

    @DELETE(DELETE_COMMENT_URL)
    suspend fun deleteComment(
        @Path(IMAGE_ID) imageId: Int,
        @Path(COMMENT_ID) commentId: Int,
    ): BaseResponse<CommentDtoOut>

    companion object MainNetworkHelper {
        fun getInstance(): MainNetworkService {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MainNetworkService::class.java)
        }
    }
}
