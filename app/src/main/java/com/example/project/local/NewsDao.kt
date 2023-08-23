package com.example.project.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
@Dao
interface NewsDao {
    @Upsert
    suspend fun upsertAll(beers: List<NewsEntity>)

    @Query("SELECT * FROM newsentity WHERE section = :section OR :section IS NULL")
    fun pagingSource(section: String?): PagingSource<Int, NewsEntity>




    @Query("DELETE  FROM newsentity")
    suspend fun clearAll()
    @Query("DELETE FROM newsentity where section=:section OR :section IS NULL")
    suspend fun clearSection(section: String?)
}