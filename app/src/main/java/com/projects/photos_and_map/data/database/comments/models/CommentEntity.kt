package com.projects.photos_and_map.data.database.comments.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity(
    val text: String,
    val date: Long,
    val imageId: Int,
    @PrimaryKey val id: Int
)
