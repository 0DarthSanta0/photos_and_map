package com.projects.photos_and_map.data.repositories.photos

import com.projects.photos_and_map.data.database.photos.models.PhotoEntity
import com.projects.photos_and_map.models.ImageDtoOut

fun ImageDtoOut.toPhotoEntity() = PhotoEntity(
    url = url,
    date = date,
    id = id,
    lng = lng,
    lat = lat
)
