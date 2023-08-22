package com.example.project.remote

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.preference.PreferenceManager
import androidx.room.withTransaction
import com.example.project.R
import com.example.project.api.ApiService
import com.example.project.api.ApiService.Companion.businessLastKey
import com.example.project.api.ApiService.Companion.generalLastKey
import com.example.project.api.ApiService.Companion.page
import com.example.project.api.ApiService.Companion.scienceLastKey
import com.example.project.api.ApiService.Companion.societyLastKey
import com.example.project.api.ApiService.Companion.sportLastKey
import com.example.project.api.ApiService.Companion.worldLAstKey
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.mappers.toNewsEntity
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
private lateinit var newsList:Response<com.example.project.remote.Response>
@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private var fromDate:String,
    private var order:String,
    private var section:String?,
    private val newsDb: NewsDataBase,
    private val newsApi: ApiService,
):RemoteMediator<Int,NewsEntity>(){
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
                        ++page
                    }
                }
            }

            Log.i("remote"," page is =$page and the load key is $loadKey")
            //setting each fragment's specific  constraint for sectuon
            if(section== "some default value"){
                section=null
             }
            Log.i("info","section is $section")
            //setting the last key if the load key is not 1
            if(loadKey!=1){
                when(section){
                    null->{
                        generalLastKey= page
                    }
                    "world"->{
                        worldLAstKey= page
                    }
                    "sport"->{
                        sportLastKey= page
                    }
                    "business"->{
                        businessLastKey= page
                    }
                    "science"->{
                        scienceLastKey=page
                    }
                    "society"->{
                        societyLastKey=page
                    }
                }
            }
            if(section!=null) {
                 newsList = newsApi.getPhotos(
                    section = section,
                    page = loadKey,
                    pageCount = state.config.pageSize,
                )
                Log.i("remote","first get called")
            }
            else {
                 newsList = newsApi.getPhotosWithOutSection(
                    page = loadKey,
                    pageCount = state.config.pageSize
                )
                Log.i("remote","second get called")
            }
            Log.i("remote","api called with load key=$loadKey&with the section of=${section}")
            newsDb.withTransaction {
                val newsEntities = newsList.body()?.response?.results?.map { it.toNewsEntity() }
                if (newsEntities != null) {
                    newsDb.dao.upsertAll(newsEntities)
                    Log.i("remote","upserted successfully")
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