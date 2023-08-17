package com.example.project.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 3)
abstract class NewsDataBase():RoomDatabase() {
    abstract val dao: NewsDao
}