package com.example.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.project.databinding.ItemBinding
import com.example.project.domain.NewsFeed
import com.example.project.viewHolder.NewsViewHolder
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class NewsAdpater(): PagingDataAdapter<NewsFeed, NewsViewHolder>(NewsComprator()) {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { news ->
            holder.bind(news)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }
}

class NewsComprator : DiffUtil.ItemCallback<NewsFeed>() {
    override fun areItemsTheSame(oldItem: NewsFeed, newItem: NewsFeed): Boolean {
        return oldItem.image == newItem.image && oldItem.body==newItem.body
    }

    override fun areContentsTheSame(oldItem: NewsFeed, newItem: NewsFeed): Boolean {
        return oldItem == newItem
    }
}