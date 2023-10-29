package com.projects.photos_and_map.domain.repositories

import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun signIn(
        login: String,
        password: String
    ): Flow<Result<Unit, AppErrors>>
    suspend fun signUp(
        login: String,
        password: String
    ): Flow<Result<Unit, AppErrors>>
    suspend fun isAuthorized(): Result<Boolean, AppErrors>
}