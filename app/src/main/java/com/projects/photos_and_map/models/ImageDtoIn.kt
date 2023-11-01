package com.projects.photos_and_map.models

data class ImageDtoIn(
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)