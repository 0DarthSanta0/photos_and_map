package com.projects.photos_and_map.data.models

data class AuthorizationResponse(
    val status: Int?,
    val data: AuthorizationResponseData?,
    val error: String?
)

data class AuthorizationResponseData(
    val userId: Int,
    val login: String,
    val token: String
)