package com.mobile.musicwiki.album.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.musicwiki.R
import com.mobile.musicwiki.album.adapter.AlbumRecyclerAdapter.AlbumViewHolder
import com.mobile.musicwiki.album.model.AlbumQuery
import com.mobile.musicwiki.databinding.ItemAlbumBinding
import com.mobile.musicwiki.album.model.AlbumResponse

class AlbumRecyclerAdapter(
    private val context: Context,
    private val albumResponse: AlbumResponse?,
    private val callBack: ((albumQuery: AlbumQuery) -> Unit?)? = null
) :
    RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            albumResponse?.albums?.album?.get(position).apply {
                Glide.with(context).load(this?.image?.get(2)?.text)
                    .placeholder(R.drawable.bg_white_rounded_rectangle_black_border)
                    .centerCrop()
                    .into(binding.ivCover)
                binding.tvAlbumName.text = this?.name
                binding.tvArtistName.text = this?.artist?.name
                val albumQuery = AlbumQuery(
                    this?.name,
                    this?.artist?.name
                )
                binding.root.setOnClickListener {
                    callBack?.invoke(albumQuery)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return albumResponse?.albums?.album?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
