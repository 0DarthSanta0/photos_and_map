package com.projects.photos_and_map.models

data class Image(
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)