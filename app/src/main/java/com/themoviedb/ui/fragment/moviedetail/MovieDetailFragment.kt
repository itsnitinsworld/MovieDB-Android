package com.themoviedb.ui.fragment.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.themoviedb.R
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.MovieDetailFragmentBinding
import com.themoviedb.ui.MainActivity
import com.themoviedb.ui.fragment.moviedetail.MovieDetailFragmentArgs.fromBundle
import com.themoviedb.ui.fragment.movielist.model.MovieResults
import com.themoviedb.utils.extensions.loadImage


class MovieDetailFragment : BaseFragment() {
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: MovieDetailFragmentBinding

    private val movieResults: MovieResults by lazy {
        fromBundle(requireArguments()).movieResult
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        return binding.root
    }

    override fun onPageRefreshListener(data: Bundle?) {
        (mContext as MainActivity).setToolbar(binding.toolbar)
        (mContext as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    override fun initListeners() {
        binding.collpasingToolbar.title = movieResults.title
        binding.movieResult = movieResults
        binding.ivMovieBanner.loadImage(movieResults.posterPath)
    }


}
