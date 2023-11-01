package com.projects.photos_and_map.network

import com.projects.photos_and_map.models.SignUserDtoIn
import com.projects.photos_and_map.models.SignUserOutDto
import com.projects.photos_and_map.models.BaseResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "https://junior.balinasoft.com/"
private const val SIGN_UP_URL = "api/account/signup"
private const val SIGN_IN_URL = "api/account/signin"

interface AuthService {

    @POST(SIGN_IN_URL)
    suspend fun signIn(
        @Body body: SignUserDtoIn
    ): BaseResponse<SignUserOutDto>

    @POST(SIGN_UP_URL)
    suspend fun signUp(
        @Body body: SignUserDtoIn
    ): BaseResponse<SignUserOutDto>

    companion object AuthHelper {
        fun getInstance(): AuthService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
        }
    }
}