package com.projects.photos_and_map.data.repositories.comments

import com.projects.photos_and_map.data.database.comments.models.CommentEntity
import com.projects.photos_and_map.models.CommentDtoOut

fun CommentDtoOut.toCommentEntity(imageId: Int) = CommentEntity(
    text = text,
    date = date,
    id = id,
    imageId = imageId
)
