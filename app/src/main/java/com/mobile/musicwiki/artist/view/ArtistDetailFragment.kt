package com.mobile.musicwiki.artist.view

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
import com.mobile.musicwiki.album.adapter.AlbumRecyclerAdapter
import com.mobile.musicwiki.album.model.AlbumResponse
import com.mobile.musicwiki.album.view.AlbumDetailFragment
import com.mobile.musicwiki.album.viewmodel.AlbumViewModel
import com.mobile.musicwiki.artist.model.ArtistDetailResponse
import com.mobile.musicwiki.artist.viewmodel.ArtistViewModel
import com.mobile.musicwiki.databinding.FragmentArtistDetailBinding
import com.mobile.musicwiki.genre.adapter.GenreRecyclerAdapter
import com.mobile.musicwiki.genre.view.GenreDetailFragment
import com.mobile.musicwiki.network.ResponseWrapper
import com.mobile.musicwiki.track.adapter.TrackRecyclerAdapter
import com.mobile.musicwiki.track.model.TrackResponse
import com.mobile.musicwiki.track.viewmodel.TrackViewModel

class ArtistDetailFragment : Fragment() {

    private lateinit var binding: FragmentArtistDetailBinding
    private val albumViewModel by activityViewModels<AlbumViewModel>()
    private val artistViewModel by activityViewModels<ArtistViewModel>()
    private val trackViewModel by activityViewModels<TrackViewModel>()
    private lateinit var albumAdapter: AlbumRecyclerAdapter
    private lateinit var trackAdapter: TrackRecyclerAdapter
    private lateinit var genreAdapter: GenreRecyclerAdapter

    private var artist: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artist = arguments?.getString(KEY_ARTIST)
        initViewModel()
    }

    private fun initViewModel() {
        albumViewModel.getAlbumResponse().observe(viewLifecycleOwner, albumObserver)
        artistViewModel.getArtistDetailResponse().observe(viewLifecycleOwner, artistObserver)
        trackViewModel.getTrackResponse().observe(viewLifecycleOwner, trackObserver)
        artist?.let { albumViewModel.fetchAlbums(method = "artist.gettopalbums", artist = it) }
        artist?.let { artistViewModel.fetchArtistDetail(artist = it) }
        artist?.let { trackViewModel.fetchTracks(method = "artist.gettoptracks", artist = it) }
    }

    private val albumObserver = Observer<ResponseWrapper<AlbumResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccessAlbum(it1) }
            }
            is ResponseWrapper.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            is ResponseWrapper.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val trackObserver = Observer<ResponseWrapper<TrackResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccessTrack(it1) }
            }
            is ResponseWrapper.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            is ResponseWrapper.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val artistObserver = Observer<ResponseWrapper<ArtistDetailResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleArtistSuccess(it1) }
            }
            is ResponseWrapper.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            is ResponseWrapper.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleArtistSuccess(artistDetailResponse: ArtistDetailResponse) {
        binding.holderToolbar.tvTitle.text = artistDetailResponse.artist?.name
        artistDetailResponse.artist.apply {
            Glide.with(requireContext()).load(this?.image?.get(2)?.text)
                .placeholder(R.drawable.bg_white_rounded_rectangle_black_border)
                .centerCrop()
                .into(binding.ivCover)
            binding.tvFollower.text = this?.stats?.listeners
            binding.tvPlayCount.text = this?.stats?.playcount
            genreAdapter = GenreRecyclerAdapter(this?.tags) { genre ->
                findNavController().navigate(
                    R.id.action_artistDetailFragment_to_genreDetailFragment,
                    GenreDetailFragment.putArguments(genre)
                )
            }
            binding.rvGenre.adapter = genreAdapter
        }

    }


    private fun handleSuccessAlbum(albumResponse: AlbumResponse) {
        albumAdapter = AlbumRecyclerAdapter(requireContext(), albumResponse) { albumQuery ->
            findNavController().navigate(
                R.id.action_artistDetailFragment_to_albumDetailFragment,
                AlbumDetailFragment.putArguments(albumQuery)
            )
        }
        binding.rvAlbums.adapter = albumAdapter
    }


    private fun handleSuccessTrack(trackResponse: TrackResponse) {
        trackAdapter = TrackRecyclerAdapter(requireContext(), trackResponse)
        binding.rvTracks.adapter = trackAdapter
    }

    companion object {
        const val KEY_ARTIST = "KEY_ARTIST"
        fun putArguments(artist: String) = Bundle().apply {
            putString(KEY_ARTIST, artist)
        }
    }
}