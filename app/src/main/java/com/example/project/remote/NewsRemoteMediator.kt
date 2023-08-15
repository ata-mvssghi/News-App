package com.example.project.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.project.api.ApiService
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.mappers.toNewsEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsDb: NewsDataBase,
    private val newsApi: ApiService
):RemoteMediator<Int,NewsEntity>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val newsList = newsApi.getPhotos(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            newsDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    newsDb.dao.clearAll()
                }
                val newsEntities = newsList.body()?.response?.results?.map { it.toNewsEntity() }
                if (newsEntities != null) {
                    newsDb.dao.upsertAll(newsEntities)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = newsList.isSuccessful
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}