package com.mobile.musicwiki.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mobile.musicwiki.R
import com.mobile.musicwiki.album.model.AlbumQuery
import com.mobile.musicwiki.album.viewmodel.AlbumViewModel
import com.mobile.musicwiki.databinding.FragmentAlbumDetailBinding
import com.mobile.musicwiki.genre.adapter.GenreRecyclerAdapter
import com.mobile.musicwiki.genre.view.GenreDetailFragment
import com.mobile.musicwiki.album.model.AlbumDetailResponse
import com.mobile.musicwiki.genre.model.Toptags
import com.mobile.musicwiki.network.ResponseWrapper

class AlbumDetailFragment : Fragment() {
    private lateinit var binding: FragmentAlbumDetailBinding
    private val albumViewModel by activityViewModels<AlbumViewModel>()
    private lateinit var adapter: GenreRecyclerAdapter

    private var albumQuery: AlbumQuery? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumQuery = arguments?.getParcelable(KEY_ALBUM)
        initViewModel()
    }

    private fun initViewModel() {
        albumViewModel.getAlbumDetailResponse().observe(viewLifecycleOwner, albumObserver)
        albumQuery?.albumName?.let {
            albumQuery?.artistName?.let { it1 ->
                albumViewModel.fetchAlbumDetail(
                    it,
                    it1
                )
            }
        }
    }

    val albumObserver = Observer<ResponseWrapper<AlbumDetailResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccess(it1) }
                it.data?.album?.tags?.let { it1 -> handleAdapter(it1) }
            }
            is ResponseWrapper.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            is ResponseWrapper.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun handleAdapter(toptags: Toptags) {
        adapter = GenreRecyclerAdapter(toptags) { genre ->
            findNavController().navigate(
                R.id.action_albumDetailFragment_to_genreDetailFragment,
                GenreDetailFragment.putArguments(genre)
            )
        }
        binding.rvGenre.adapter = adapter
    }

    private fun handleSuccess(albumDetailResponse: AlbumDetailResponse) {
        albumDetailResponse.album.apply {
            Glide.with(requireContext()).load(this?.image?.get(2)?.text)
                .placeholder(R.drawable.bg_white_rounded_rectangle_black_border)
                .centerCrop()
                .into(binding.ivCover)
            binding.tvArtistName.text = this?.artist
            binding.holderToolbar.tvTitle.text = this?.name
            binding.tvAlbumDescription.text = this?.wiki?.summary
        }

    }

    companion object {
        const val KEY_ALBUM = "KEY_ALBUM"
        fun putArguments(album: AlbumQuery) = Bundle().apply {
            putParcelable(KEY_ALBUM, album)
        }
    }
}