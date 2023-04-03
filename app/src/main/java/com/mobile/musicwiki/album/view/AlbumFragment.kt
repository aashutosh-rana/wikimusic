package com.mobile.musicwiki.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.musicwiki.Helper.showToast
import com.mobile.musicwiki.album.adapter.AlbumRecyclerAdapter
import com.mobile.musicwiki.album.model.AlbumQuery
import com.mobile.musicwiki.album.model.AlbumResponse
import com.mobile.musicwiki.album.viewmodel.AlbumViewModel
import com.mobile.musicwiki.databinding.FragmentAlbumBinding
import com.mobile.musicwiki.network.ResponseWrapper

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    var callBack: Callback? = null
    private lateinit var adapter: AlbumRecyclerAdapter
    private val albumViewModel by activityViewModels<AlbumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        albumViewModel.getAlbumResponse().observe(viewLifecycleOwner, albumObserver)
    }

    private val albumObserver = Observer<ResponseWrapper<AlbumResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccess(it1) }
            }
            is ResponseWrapper.Error -> {
                requireContext().showToast("went wrong while getting albums")
            }
            is ResponseWrapper.Loading -> {
            }
        }
    }

    private fun handleSuccess(albumResponse: AlbumResponse) {
        adapter = AlbumRecyclerAdapter(requireContext(), albumResponse) { albumQuery ->
            callBack?.onItemClicked(albumQuery)
        }
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.root.layoutManager = layoutManager
        binding.root.adapter = adapter
    }


    interface Callback {
        fun onItemClicked(albumQuery: AlbumQuery)
    }


    companion object {
        fun newInstance() = AlbumFragment()
    }
}