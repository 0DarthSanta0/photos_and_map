package com.projects.photos_and_map

import android.app.Application
import android.content.Context

class PhotosAndMapApplication: Application()  {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        private lateinit var instance: PhotosAndMapApplication

        fun applicationContext() : Context = instance.applicationContext
    }

}