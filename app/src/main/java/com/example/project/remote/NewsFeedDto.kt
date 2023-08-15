package com.example.project.remote
data class NewsFeedDto( val  id: String,
                     val type: String,
                     val sectionId: String,
                     val sectionName: String,
                     val webPublicationDate: String,
                     val webTitle: String,
                     val webUrl: String,
                     val apiUrl: String,
                     val fields: Fields,
                     val isHosted: Boolean,
                     val pillarId: String,
                     val pillarName: String)
data class Fields(
    val headline:String,
    val trailText:String,
    val thumbnail:String,
    val publication:String)