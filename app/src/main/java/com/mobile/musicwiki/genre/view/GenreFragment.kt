package com.mobile.musicwiki.genre.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.musicwiki.R
import com.mobile.musicwiki.databinding.FragmentGenreBinding
import com.mobile.musicwiki.genre.adapter.GenreRecyclerAdapter
import com.mobile.musicwiki.genre.viewmodel.GenreViewModel
import com.mobile.musicwiki.genre.model.GenreResponse
import com.mobile.musicwiki.genre.model.Toptags
import com.mobile.musicwiki.network.ResponseWrapper


class GenreFragment : Fragment() {

    private lateinit var binding: FragmentGenreBinding
    private lateinit var adapter: GenreRecyclerAdapter
    private val genreViewModel by activityViewModels<GenreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        genreViewModel.getAllGenreResponse().observe(viewLifecycleOwner, genreObserver)
        genreViewModel.fetchAllGenres()
    }

    private val genreObserver = Observer<ResponseWrapper<GenreResponse>> {
        when (it) {
            is ResponseWrapper.Success -> {
                it.data?.let { it1 -> handleSuccess(it1.toptags) }
            }
            is ResponseWrapper.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            is ResponseWrapper.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSuccess(toptags: Toptags) {
        adapter = GenreRecyclerAdapter(toptags) { genre ->
            findNavController().navigate(
                R.id.action_genreFragment_to_genreDetailFragment,
                GenreDetailFragment.putArguments(genre)
            )
        }
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvGenre.layoutManager = layoutManager
        binding.rvGenre.adapter = adapter
    }

}