package com.themoviedb.ui.fragment.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.themoviedb.R
import com.themoviedb.adapter.MoviesListAdapter
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.MovieListFragmentBinding
import com.themoviedb.model.MovieResults
import com.themoviedb.ui.MainActivity
import com.themoviedb.utils.extensions.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_list_fragment.*

@AndroidEntryPoint
class MovieListFragment : BaseFragment() {
    private lateinit var binding: MovieListFragmentBinding

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var mAdapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        return binding.root
    }


    override fun onPageRefreshListener(data: Bundle?) {
        (mContext as MainActivity).setToolbar()
    }

    override fun initListeners() {
        val layoutManager = LinearLayoutManager(context)
        mAdapter =
            MoviesListAdapter {
                val direction =
                    MovieListFragmentDirections.actionToMovieDetail(it.tag as MovieResults)
                findNavController().navigate(direction)
            }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = mAdapter
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.isViewLoading.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = it
            binding.shimmerContainer.makeVisible(it)
            if (it)
                binding.shimmerContainer.startShimmer()
            else binding.shimmerContainer.stopShimmer()
        })

        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            mAdapter.addAll(it)
        })

        viewModel.onMessageError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })


        viewModel.isEmptyList.observe(viewLifecycleOwner, Observer {
            binding.ivNoData.makeVisible(it)
        })
    }

}
