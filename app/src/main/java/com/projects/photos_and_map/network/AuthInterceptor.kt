package com.projects.photos_and_map.network

import com.projects.photos_and_map.data.data_store.DataStoreManagerImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


private const val AUTH = "Access-Token"
private const val TOKEN_KEY = "token"

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { DataStoreManagerImpl.getString(TOKEN_KEY) }
        val request = chain.request().newBuilder()
            .addHeader(AUTH, token)
            .build()
        return chain.proceed(request)
    }
}
