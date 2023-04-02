package com.mobile.musicwiki.track.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.musicwiki.R
import com.mobile.musicwiki.databinding.ItemAlbumBinding
import com.mobile.musicwiki.track.model.TrackResponse

class TrackRecyclerAdapter(
    private val context: Context,
    private val trackResponse: TrackResponse?,
    private val callBack: ((position: Int) -> Unit?)? = null
) : RecyclerView.Adapter<TrackRecyclerAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TrackViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TrackViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            trackResponse?.tracks?.track?.get(position).apply {
                Glide.with(context).load(this?.image?.get(2)?.text)
                    .placeholder(R.drawable.bg_white_rounded_rectangle_black_border).centerCrop()
                    .into(binding.ivCover)
                binding.tvAlbumName.text = this?.name
                binding.tvArtistName.text = this?.artist?.name
            }
            binding.root.setOnClickListener {
                callBack?.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return trackResponse?.tracks?.track?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
