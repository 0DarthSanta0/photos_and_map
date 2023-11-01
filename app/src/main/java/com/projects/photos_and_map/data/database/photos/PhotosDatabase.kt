package com.projects.photos_and_map.data.database.photos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.photos_and_map.data.database.photos.dao.PhotoDao
import com.projects.photos_and_map.data.database.photos.models.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 1
)
abstract class PhotosDatabase: RoomDatabase() {
    abstract fun dao(): PhotoDao
}
