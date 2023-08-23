package com.example.project.api

import android.util.Log
import com.example.project.api.ApiService.Companion.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

import java.util.Locale.Category
import kotlin.text.Typography.section



// val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .baseUrl(BASE_URL)
    .build()


interface ApiService {
    @GET("search?show-fields=headline,trailText,thumbnail,publication&api-key=953f8d3f-32dd-40b0-b440-85b51226b148")
    suspend fun getPhotos(
        @Query("from-date") fromDate:String,
        @Query("order-by") order:String?="newest",
        @Query("section") section: String? = null,
        @Query("page") page: Int,
        @Query("page-size") pageCount: Int
    ):retrofit2.Response<com.example.project.remote.Response>

    @GET("search?show-fields=headline,trailText,thumbnail,publication&api-key=953f8d3f-32dd-40b0-b440-85b51226b148")
    suspend fun getPhotosWithOutSection(
        @Query("from-date") fromDate:String,
        @Query("order-by") order:String?,
        @Query("page") page: Int,
        @Query("page-size") pageCount: Int
    ):retrofit2.Response<com.example.project.remote.Response>

    companion object {
        const val BASE_URL = "https://content.guardianapis.com/"
         var page:Int=1
        var generalLastKey=0
        var worldLAstKey=0
        var sportLastKey=0
        var businessLastKey=0
        var scienceLastKey=0
        var societyLastKey=0
        var date="init"
    }
}
