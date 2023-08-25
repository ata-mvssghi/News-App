package com.example.project.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.databinding.ItemBinding
import com.example.project.domain.NewsFeed
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdpater(): PagingDataAdapter<NewsFeed, NewsAdpater.NewsViewHolder>(NewsComprator()) {
    private var onItemClickListener: ((NewsFeed) -> Unit)? = null
   inner class NewsViewHolder(private val binding: ItemBinding) :
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
            binding.authorTv.text = news.author
            binding.dateTv.text = formattedDate
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(news)
            }
            binding.shareIcon.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, news.webUrl)
                itemView.context.startActivity(Intent.createChooser(intent, "Share via"))
            }
        }
    }
    fun setOnItemClickListener(listener: (NewsFeed) -> Unit) {
        onItemClickListener = listener
    }

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
