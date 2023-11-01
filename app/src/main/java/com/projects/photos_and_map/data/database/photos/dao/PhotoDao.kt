package com.projects.photos_and_map.data.database.photos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.projects.photos_and_map.data.database.photos.models.PhotoEntity

@Dao
interface PhotoDao {
    @Upsert
    fun upsertPhoto(photoEntity: PhotoEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfPhotos(photos: List<PhotoEntity>)
    @Delete
    fun deleteImage(image: PhotoEntity)
    @Query("SELECT * FROM PhotoEntity ORDER BY id DESC LIMIT :limit")
    fun selectPhotos(limit: Int): List<PhotoEntity>
    @Query("SELECT * FROM PhotoEntity ORDER BY id DESC")
    fun selectAllPhotos(): List<PhotoEntity>
    @Query("SELECT * FROM PhotoEntity WHERE id = :id")
    fun selectPhoto(id: Int): PhotoEntity
}
