package com.mobile.musicwiki.genre.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.musicwiki.Helper.getSliced
import com.mobile.musicwiki.Helper.onBackPressed
import com.mobile.musicwiki.Helper.showToast
import com.mobile.musicwiki.R
import com.mobile.musicwiki.album.model.AlbumQuery
import com.mobile.musicwiki.album.model.AlbumResponse
import com.mobile.musicwiki.album.view.AlbumDetailFragment
import com.mobile.musicwiki.album.view.AlbumFragment
import com.mobile.musicwiki.album.viewmodel.AlbumViewModel
import com.mobile.musicwiki.artist.model.ArtistResponse
import com.mobile.musicwiki.artist.view.ArtistDetailFragment
import com.mobile.musicwiki.artist.view.ArtistFragment
import com.mobile.musicwiki.artist.viewmodel.ArtistViewModel
import com.mobile.musicwiki.databinding.FragmentGenreDetailBinding
import com.mobile.musicwiki.genre.model.GenreDetailResponse
import com.mobile.musicwiki.genre.viewmodel.GenreViewModel
import com.mobile.musicwiki.network.ResponseWrapper
import com.mobile.musicwiki.track.model.TrackResponse
import com.mobile.musicwiki.track.view.TrackFragment
import com.mobile.musicwiki.track.viewmodel.TrackViewModel

class GenreDetailFragment : Fragment() {

    private lateinit var binding: FragmentGenreDetailBinding
    private val genreViewModel by activityViewModels<GenreViewModel>()
    var genre: String? = null
    private val albumViewModel by activityViewModels<AlbumViewModel>()
    private val artistViewModel by activityViewModels<ArtistViewModel>()
    private val trackViewModel by activityViewModels<TrackViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genre = arguments?.getString(KEY_GENRE)
        initViewModel()
        handleBackButton()
    }

    private fun initView() {
        binding.viewPager.adapter = GenreViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(
                when (position) {
                    0 -> R.string.albums
                    1 -> R.string.artists
                    else -> R.string.tracks
                }
            )
        }.attach()
    }

    private fun handleBackButton() {
        binding.holderToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewModel() {
        genreViewModel.getGenreDetailResponse().observe(viewLifecycleOwner, genreObserver)
        albumViewModel.getAlbumResponse().observe(viewLifecycleOwner, albumObserver)
        artistViewModel.getArtistResponse().observe(viewLifecycleOwner, artistObserver)
        trackViewModel.getTrackResponse().observe(viewLifecycleOwner, trackObserver)
        genre?.let { genreViewModel.fetchGenreDetail(genre = it) }
        genre?.let { albumViewModel.fetchAlbums(genre = it) }
        genre?.let { artistViewModel.fetchArtist(genre = it) }
        genre?.let { trackViewModel.fetchTracks(genre = it) }
    }

    private val genreObserver = Observer<ResponseWrapper<GenreDetailResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                binding.holderToolbar.tvTitle.text = it.data?.tag?.name
                binding.tvDetail.text = it.data?.tag?.wiki?.summary?.let { it1 -> getSliced(it1) }
                binding.holderProgress.root.visibility = View.GONE
                binding.scrollParent.visibility = View.VISIBLE
                initView()
            }
            is ResponseWrapper.Error -> {
                requireContext().showToast()
                binding.holderProgress.root.visibility = View.GONE
            }
            is ResponseWrapper.Loading -> {
                binding.holderProgress.root.visibility = View.VISIBLE
                binding.scrollParent.visibility = View.GONE
            }
        }
    }
    private val albumObserver = Observer<ResponseWrapper<AlbumResponse>> {}
    private val artistObserver = Observer<ResponseWrapper<ArtistResponse>> {}
    private val trackObserver = Observer<ResponseWrapper<TrackResponse>> {}


    val trackFragment by lazy {
        TrackFragment.newInstance().also {
            it.callback = object : TrackFragment.Callback {
                override fun onItemClicked() {

                }

            }
        }
    }

    val albumFragment by lazy {
        AlbumFragment.newInstance().also {
            it.callBack = object : AlbumFragment.Callback {
                override fun onItemClicked(albumQuery: AlbumQuery) {
                    findNavController().navigate(
                        R.id.action_genreDetailFragment_to_albumDetailFragment,
                        AlbumDetailFragment.putArguments(albumQuery)
                    )
                }

            }
        }
    }

    val artistFragment by lazy {
        ArtistFragment.newInstance().also {
            it.callBack = object : ArtistFragment.Callback {
                override fun onItemClicked(artist: String) {
                    findNavController().navigate(
                        R.id.action_genreDetailFragment_to_artistDetailFragment,
                        ArtistDetailFragment.putArguments(artist)
                    )
                }

            }
        }
    }

    inner class GenreViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> albumFragment
                1 -> artistFragment
                else -> trackFragment
            }
        }
    }

    companion object {
        const val KEY_GENRE = "KEY_GENRE"
        fun putArguments(genre: String) = Bundle().apply {
            putString(KEY_GENRE, genre)
        }
    }
}
