package com.example.project.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 2)
abstract class NewsDataBase():RoomDatabase() {
    abstract val dao: NewsDao
}