package com.example.project.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
@Dao
interface NewsDao {
    @Upsert
    suspend fun upsertAll(beers: List<NewsEntity>)

    @Query("SELECT * FROM newsentity")
    fun pagingSource(): PagingSource<Int, NewsEntity>

    @Query("DELETE FROM newsentity")
    suspend fun clearAll()
}