package com.example.project.domain

import com.google.gson.annotations.SerializedName

data class NewsFeed(
    val image:String?,
    val title:String,
    val body:String,
    val author:String)
