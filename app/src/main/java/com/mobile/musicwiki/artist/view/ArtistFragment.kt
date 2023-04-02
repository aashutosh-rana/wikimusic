package com.mobile.musicwiki.artist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.musicwiki.artist.adapter.ArtistRecyclerAdapter
import com.mobile.musicwiki.artist.viewmodel.ArtistViewModel
import com.mobile.musicwiki.databinding.FragmentArtistBinding
import com.mobile.musicwiki.artist.model.ArtistResponse
import com.mobile.musicwiki.network.ResponseWrapper

class ArtistFragment : Fragment() {

    private lateinit var binding: FragmentArtistBinding
    var callBack: Callback? = null
    private lateinit var adapter: ArtistRecyclerAdapter
    private val artistViewModel by activityViewModels<ArtistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        artistViewModel.getArtistResponse().observe(viewLifecycleOwner, artistObserver)
    }

    private val artistObserver = Observer<ResponseWrapper<ArtistResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccess(it1) }
            }
            is ResponseWrapper.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            is ResponseWrapper.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSuccess(artistResponse: ArtistResponse) {
        adapter = ArtistRecyclerAdapter(requireContext(), artistResponse) { artist ->
            callBack?.onItemClicked(artist)
        }
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.root.layoutManager = layoutManager
        binding.root.adapter = adapter
    }

    interface Callback {
        fun onItemClicked(artist: String)
    }

    companion object {
        fun newInstance() = ArtistFragment()
    }
}