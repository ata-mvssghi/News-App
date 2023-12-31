package com.example.project.remote

import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.log
import androidx.preference.PreferenceManager
import androidx.room.withTransaction
import com.bumptech.glide.Glide.init
import com.example.project.R
import com.example.project.api.ApiService
import com.example.project.api.ApiService.Companion.date
import com.example.project.api.ApiService.Companion.businessLastKey
import com.example.project.api.ApiService.Companion.generalLastKey
import com.example.project.api.ApiService.Companion.order
import com.example.project.api.ApiService.Companion.page
import com.example.project.api.ApiService.Companion.scienceLastKey
import com.example.project.api.ApiService.Companion.societyLastKey
import com.example.project.api.ApiService.Companion.sportLastKey
import com.example.project.api.ApiService.Companion.worldLAstKey
import com.example.project.fragmnets.SettingsFragment
import com.example.project.fragmnets.onApiSettingChangedListner
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.mappers.toNewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

//private lateinit var newsList:Response<com.example.project.remote.Response>
@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private var fromDate:String,
    private var order:String?,
    private var section:String?,
    private val newsDb: NewsDataBase,
    private val newsApi: ApiService,
):RemoteMediator<Int,NewsEntity>(),onApiSettingChangedListner{
    init {
        SettingsFragment.ApiChangeInstance.apiChangeListener=this
        date=fromDate
        ApiService.order=order
    }
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
            val newsList :Response<com.example.project.remote.Response>
            if(section!=null) {
                 newsList = newsApi.getPhotos(
                     fromDate= date,
                     order=ApiService.order,
                    section = section,
                    page = loadKey,
                    pageCount = state.config.pageSize,
                )
                Log.i("remote","first get called with fromDate of =$date, order=${ApiService.order}")
            }
            else {
                 newsList = newsApi.getPhotosWithOutSection(
                     fromDate= date,
                     order=ApiService.order,
                    page = loadKey,
                    pageCount = state.config.pageSize
                )
                Log.i("remote","second get called with fromDate of =$date, order=${ApiService.order}")
            }
            Log.i("remote","api called with load key=$loadKey&with the section of=${section} and form fate+$fromDate")
            try {
                newsDb.withTransaction {
                    val newsEntities = newsList.body()?.response?.results?.map { it.toNewsEntity() }
                    if (newsEntities != null) {
                        newsDb.dao.upsertAll(newsEntities)
                        Log.i("remote", "upserted successfully")
                    }
                }
            }
            catch (e:Exception){
                Log.e("remote","${e.message}")
            }
                val endOfPaginationReached = newsList.body()?.response?.results?.isEmpty() ?: true
            Log.i("remote","end reached=$endOfPaginationReached")
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch(e: IOException) { MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
      suspend fun update(){
        withContext(Dispatchers.IO) {
          newsDb.dao.clearAll()
            ApiService.page=1
           // newsDb.dao.clearSection(section)
            Log.i("remote","done with deleting the section =$section from db")
        }
    }

    override fun onUpdate(newDate: String, newOrder: String?) {
        date=newDate
        ApiService.order=newOrder
        Log.i("remote ","value passed to remmote mediator : date=$newDate, order =$newOrder \n from date now is $fromDate")
        GlobalScope.launch {
            update()
        }

    }
}