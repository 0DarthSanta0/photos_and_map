package com.projects.photos_and_map.data.repositories.photos

import com.projects.photos_and_map.data.database.photos.models.PhotoEntity
import com.projects.photos_and_map.models.Image

fun PhotoEntity.toImage() = Image(
    url = url,
    date = date,
    id = id,
    lat = lat,
    lng = lng
)
