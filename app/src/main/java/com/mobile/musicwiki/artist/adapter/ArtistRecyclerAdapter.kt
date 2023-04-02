package com.mobile.musicwiki.artist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.musicwiki.R
import com.mobile.musicwiki.databinding.ItemArtistBinding
import com.mobile.musicwiki.artist.model.ArtistResponse

class ArtistRecyclerAdapter(
    private val context: Context,
    private val artistResponse: ArtistResponse?,
    private val callBack: ((artist: String) -> Unit?)? = null
) :
    RecyclerView.Adapter<ArtistRecyclerAdapter.ArtistViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            artistResponse?.topartists?.artist?.get(position).apply {
                Glide.with(context).load(this?.image?.get(2)?.text)
                    .placeholder(R.drawable.bg_white_rounded_rectangle_black_border)
                    .centerCrop()
                    .into(binding.ivCover)
                binding.tvArtistName.text = this?.name
                binding.root.setOnClickListener {
                    this?.name?.let { it1 -> callBack?.invoke(it1) }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return artistResponse?.topartists?.artist?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
