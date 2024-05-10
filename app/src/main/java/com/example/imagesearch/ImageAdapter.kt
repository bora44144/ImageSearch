package com.example.imagesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.RecyclerviewItemBinding

class ImageAdapter(val items: MutableList<ImageInfo>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageInfo) {
            Glide.with(itemView)
                .load(item.thumbnail_url)
                .into(binding.ivThumbnail)
            binding.tvSiteName.text = item.toString()
            binding.tvDateTime.text = item.toString()
        }
    }

}