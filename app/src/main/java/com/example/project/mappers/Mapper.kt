package com.example.project.mappers

import com.example.project.domain.NewsFeed
import com.example.project.local.NewsEntity
import com.example.project.remote.NewsFeedDto

    fun NewsFeedDto.toNewsEntity():NewsEntity{
        return com.example.project.local.NewsEntity(
            image = fields.thumbnail,
            title = fields.headline,
            body =  fields.trailText,
            author = fields.publication,
            section = sectionId,
            date = webPublicationDate
        )
    }

    fun NewsEntity.toNewsFeed():NewsFeed{
        return NewsFeed(
            image=image, title=title, body=body, author=author,date=date
        )
    }