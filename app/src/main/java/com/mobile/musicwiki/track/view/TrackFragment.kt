package com.mobile.musicwiki.track.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.musicwiki.databinding.FragmentAlbumBinding
import com.mobile.musicwiki.track.model.TrackResponse
import com.mobile.musicwiki.network.ResponseWrapper
import com.mobile.musicwiki.track.adapter.TrackRecyclerAdapter
import com.mobile.musicwiki.track.viewmodel.TrackViewModel

class TrackFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    var callback: Callback? = null
    private lateinit var adapter: TrackRecyclerAdapter
    private val trackViewModel by activityViewModels<TrackViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(layoutInflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        trackViewModel.getTrackResponse().observe(viewLifecycleOwner, trackObserver)
    }

    private val trackObserver = Observer<ResponseWrapper<TrackResponse>> {
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

    private fun handleSuccess(trackResponse: TrackResponse) {
        adapter = TrackRecyclerAdapter(requireContext(), trackResponse)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.root.layoutManager = layoutManager
        binding.root.adapter = adapter
    }


    interface Callback {
        fun onItemClicked()
    }

    companion object {
        fun newInstance() = TrackFragment()
    }
}