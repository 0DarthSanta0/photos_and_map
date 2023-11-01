package com.projects.photos_and_map.data.database.photos.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double,
    @PrimaryKey val id: Int
)
