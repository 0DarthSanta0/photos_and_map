package com.projects.photos_and_map.data.repositories.comments

import com.projects.photos_and_map.data.database.comments.models.CommentEntity
import com.projects.photos_and_map.models.Comment

fun CommentEntity.toComment() = Comment(
    text = text,
    date = date,
    id = id
)
