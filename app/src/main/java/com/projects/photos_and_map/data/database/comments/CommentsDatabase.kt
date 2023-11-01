package com.projects.photos_and_map.data.database.comments

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.photos_and_map.data.database.comments.dao.CommentDao
import com.projects.photos_and_map.data.database.comments.models.CommentEntity

@Database(
    entities = [CommentEntity::class],
    version = 1
)
abstract class CommentsDatabase: RoomDatabase() {
    abstract fun dao(): CommentDao
}
