package com.mobile.musicwiki.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mobile.musicwiki.Helper.getSliced
import com.mobile.musicwiki.Helper.onBackPressed
import com.mobile.musicwiki.Helper.showToast
import com.mobile.musicwiki.R
import com.mobile.musicwiki.album.model.AlbumDetailResponse
import com.mobile.musicwiki.album.model.AlbumQuery
import com.mobile.musicwiki.album.viewmodel.AlbumViewModel
import com.mobile.musicwiki.databinding.FragmentAlbumDetailBinding
import com.mobile.musicwiki.genre.adapter.GenreRecyclerAdapter
import com.mobile.musicwiki.genre.model.Toptags
import com.mobile.musicwiki.genre.view.GenreDetailFragment
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
        handleBackButton()
    }

    private fun handleBackButton() {
        binding.holderToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
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

    private val albumObserver = Observer<ResponseWrapper<AlbumDetailResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccess(it1) }
                it.data?.album?.tags?.let { it1 -> handleAdapter(it1) }
                binding.llUi.visibility = View.VISIBLE
                binding.holderProgress.root.visibility = View.GONE
            }
            is ResponseWrapper.Error -> {
                binding.holderProgress.root.visibility = View.GONE
                requireContext().showToast()
            }
            is ResponseWrapper.Loading -> {
                binding.llUi.visibility = View.GONE
                binding.holderProgress.root.visibility = View.VISIBLE
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
            binding.tvAlbumDescription.text =
                this?.wiki?.summary?.let { getSliced(it) }
        }

    }

    companion object {
        const val KEY_ALBUM = "KEY_ALBUM"
        fun putArguments(album: AlbumQuery) = Bundle().apply {
            putParcelable(KEY_ALBUM, album)
        }
    }
}