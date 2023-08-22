package com.example.firstplaytrough

import android.app.Application
import androidx.room.Room
import com.example.firstplaytrough.model.AppDatabase

class MyApp : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
            .build()
    }
}
