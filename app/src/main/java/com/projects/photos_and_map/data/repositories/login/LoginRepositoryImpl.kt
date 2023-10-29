package com.projects.photos_and_map.data.repositories.login

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.data.data_store.DataStoreManager
import com.projects.photos_and_map.data.models.AuthorizationBody
import com.projects.photos_and_map.data.models.AuthorizationResponse
import com.projects.photos_and_map.data.repositories.catchDataBaseErrors
import com.projects.photos_and_map.domain.repositories.LoginRepository
import com.projects.photos_and_map.network.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

private const val TOKEN_KEY = "token"

class LoginRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val retrofitAPI: NetworkService = NetworkService.getInstance()
) : LoginRepository {

    override suspend fun signIn(login: String, password: String): Flow<Result<Unit, AppErrors>> =
        flow {
            try {
                val response = retrofitAPI.signIn(
                    body = AuthorizationBody(
                        login = login,
                        password = password
                    )
                )
                emit(onResponse(response))
            } catch (e: HttpException) {
                emit(
                    Err(AppErrors.ResponseError)
                )
            }
        }

    override suspend fun signUp(login: String, password: String): Flow<Result<Unit, AppErrors>> =
        flow {
            try {
                val response = retrofitAPI.signUp(
                    body = AuthorizationBody(
                        login = login,
                        password = password
                    )
                )
                emit(onResponse(response))
            } catch (e: HttpException) {
                emit(
                    Err(AppErrors.ResponseError)
                )
            }
        }

    override suspend fun isAuthorized(): Result<Boolean, AppErrors> =
        catchDataBaseErrors {
            dataStoreManager.getString(TOKEN_KEY).isNotEmpty()
        }

    private suspend fun onResponse(response: AuthorizationResponse): Result<Unit, AppErrors> {
        return if (response.data != null) {
            saveToken(response.data.token)
            Ok(Unit)
        } else {
            Err(AppErrors.ResponseError)
        }
    }

    private suspend fun saveToken(token: String) {
        dataStoreManager.saveString(key = TOKEN_KEY, value = token)
    }

}
