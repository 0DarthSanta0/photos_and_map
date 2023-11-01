package com.projects.photos_and_map.data.database.comments.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.projects.photos_and_map.data.database.comments.models.CommentEntity

@Dao
interface CommentDao {
    @Upsert
    fun upsertComment(commentEntity: CommentEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfComments(comments: List<CommentEntity>)
    @Delete
    fun deleteComment(commentEntity: CommentEntity)
    @Query("SELECT * FROM CommentEntity WHERE imageId = :imageId ORDER BY id DESC LIMIT :limit")
    fun selectComments(limit: Int, imageId: Int): List<CommentEntity>
    @Query("SELECT * FROM CommentEntity WHERE id = :id")
    fun selectComment(id: Int): CommentEntity
}
