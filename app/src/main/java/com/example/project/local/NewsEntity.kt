package com.example.project.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val image:String?,
    val title:String,
    val body:String,
    val author:String,
    val section:String?,
    val date:String,
    val webUrl:String
        )