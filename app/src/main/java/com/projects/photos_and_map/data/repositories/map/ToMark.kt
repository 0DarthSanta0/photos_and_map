package com.projects.photos_and_map.data.repositories.map

import com.projects.photos_and_map.data.database.photos.models.PhotoEntity
import com.projects.photos_and_map.models.Mark

fun PhotoEntity.toMark() = Mark(
    lat = lat,
    lng = lng
)
