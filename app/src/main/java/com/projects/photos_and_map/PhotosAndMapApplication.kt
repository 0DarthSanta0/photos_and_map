package com.projects.photos_and_map

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.projects.photos_and_map.data.database.comments.CommentsDatabase
import com.projects.photos_and_map.data.database.comments.dao.CommentDao
import com.projects.photos_and_map.data.database.photos.PhotosDatabase
import com.projects.photos_and_map.data.database.photos.dao.PhotoDao


private const val PHOTOS_DATABASE_NAME = "photo-database"
private const val COMMENTS_DATABASE_NAME = "comments-database"

class PhotosAndMapApplication: Application()  {

    override fun onCreate() {
        instance = this
        super.onCreate()
        photoDao = Room.databaseBuilder(
            this,
            PhotosDatabase::class.java, PHOTOS_DATABASE_NAME
        ).allowMainThreadQueries().build().dao()
        commentDao = Room.databaseBuilder(
            this,
            CommentsDatabase::class.java, COMMENTS_DATABASE_NAME
        ).allowMainThreadQueries().build().dao()
    }

    companion object {
        private lateinit var instance: PhotosAndMapApplication
        private lateinit var photoDao: PhotoDao
        private lateinit var commentDao: CommentDao

        fun applicationContext() : Context = instance.applicationContext

        fun getPhotoDao(): PhotoDao = photoDao

        fun getCommentDao(): CommentDao = commentDao

    }

}