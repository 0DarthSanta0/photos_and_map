package com.projects.photos_and_map.data.data_store


interface DataStoreManager {
    suspend fun getString(key: String): String
    suspend fun saveString(key: String, value: String)
}
