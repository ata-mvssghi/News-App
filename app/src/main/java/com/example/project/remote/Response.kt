package com.example.project.remote

import com.example.project.domain.NewsFeed

data class Response(val response: SecondLayer?)
data class SecondLayer(  val status: String,
                         val userTier: String,
                         val total: Int,
                         val startIndex: Int,
                         val pageSize: Int,
                         val currentPage: Int,
                         val pages: Int,
                         val orderBy: String,
                         val results:List<NewsFeedDto>
                         )