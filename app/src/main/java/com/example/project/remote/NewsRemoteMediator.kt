package com.example.project.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.project.R
import com.example.project.api.ApiService
import com.example.project.api.ApiService.Companion.page
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.mappers.toNewsEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private var section:String?,
    private val newsDb: NewsDataBase,
    private val newsApi: ApiService
):RemoteMediator<Int,NewsEntity>(){
     var lastLoadKey=1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>,
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
                        ++ApiService.page
                    }
                }
            }
            Log.i("remote"," page is =${ApiService.page} and the load key is $loadKey")
            //setting each fragment's specific  constraint for sectuon
            val queryMap = mutableMapOf<String, String>()
            if(section== "some default value"){
                section=null
             }
            Log.i("ifo","section is $section")
            section?.let {
                queryMap["section"] = it
            }
            val newsList = newsApi.getPhotos(
                queryMap=queryMap,
                page = loadKey,
                pageCount = state.config.pageSize
            )
            Log.i("remote","api called with load key=$loadKey&with the section of=${queryMap.values}")
            newsDb.withTransaction {
                val newsEntities = newsList.body()?.response?.results?.map { it.toNewsEntity() }
                if (newsEntities != null) {
                    newsDb.dao.upsertAll(newsEntities)
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = newsList.body()?.response?.results!!.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}