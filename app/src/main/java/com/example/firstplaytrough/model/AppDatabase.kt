package com.example.firstplaytrough.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Account::class, Game::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun gameDao(): GameDao
}
