package com.example.project.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsFeed(
    val image:String?,
    val title:String,
    val body:String,
    val author:String,
    val date:String,
    val webUrl:String
    ):Serializable
