package com.projects.photos_and_map.models

data class SignUserOutDto(
    val userId: Int,
    val login: String,
    val token: String
)