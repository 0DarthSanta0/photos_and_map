package com.projects.photos_and_map.models

data class BaseResponse<T>(
    val status: Int?,
    val data: T?,
    val error: String?
)
