package com.mobile.musicwiki.genre.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.musicwiki.databinding.ItemGenreBinding
import com.mobile.musicwiki.genre.model.Toptags

class GenreRecyclerAdapter(
    private val topTags: Toptags?,
    private val callBack: ((genre: String) -> Unit?)? = null
) :
    RecyclerView.Adapter<GenreRecyclerAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class GenreViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            if (position < 10) {
                binding.root.text = topTags?.tag?.get(position)?.name
                binding.root.setOnClickListener {
                    topTags?.tag?.get(position)?.name?.let { it1 -> callBack?.invoke(it1) }
                }
            } else {
                binding.root.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return topTags?.tag?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
