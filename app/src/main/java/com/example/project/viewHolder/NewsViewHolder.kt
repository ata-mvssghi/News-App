package com.example.project.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.databinding.ItemBinding
import com.example.project.domain.NewsFeed
import java.text.SimpleDateFormat
import java.util.Locale

class NewsViewHolder(private val binding: ItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(news: NewsFeed) {
        //format the date received from NewsFeed instance
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputDateFormat.parse(news.date)
        val formattedDate = outputDateFormat.format(date)
        // Load image using Glide or your preferred image loading library
        Glide.with(binding.root.context)
            .load(news.image)
            .into(binding.imageView)
        binding.titleTv.text = news.title
        binding.descriptionTv.text = news.body
        binding.authorTv.text=news.author
        binding.dateTv.text=formattedDate
    }
    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val binding = ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NewsViewHolder(binding)
        }
    }
}