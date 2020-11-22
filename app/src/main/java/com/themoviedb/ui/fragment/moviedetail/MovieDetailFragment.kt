package com.themoviedb.ui.fragment.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.themoviedb.R
import com.themoviedb.adapter.CastListAdapter
import com.themoviedb.adapter.ProductionAdapter
import com.themoviedb.adapter.SimilarMoviesAdapter
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.MovieDetailFragmentBinding
import com.themoviedb.dialog.ReviewsBottomDialogFragment
import com.themoviedb.model.*
import com.themoviedb.ui.MainActivity
import com.themoviedb.ui.fragment.moviedetail.MovieDetailFragmentArgs.fromBundle
import com.themoviedb.utils.ToastUtils
import com.themoviedb.utils.extensions.loadImage
import com.themoviedb.utils.extensions.makeVisible
import com.themoviedb.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private lateinit var binding: MovieDetailFragmentBinding
    private var mCastAdapter: CastListAdapter? = null
    private var mSimilarMoviesAdapter: SimilarMoviesAdapter? = null
    private var mProductionAdapter: ProductionAdapter? = null

    private val movieResults: MovieResults by lazy {
        fromBundle(requireArguments()).movieResult
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDetails(movieId = movieResults.id.toString())
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
        binding.collpasingToolbar.title = movieResults.title
    }


    override fun initListeners() {
        binding.movieResult = movieResults
        binding.ivMovieBanner.loadImage(movieResults.posterPath)

        binding.llMovieDetailContent.findViewById<AppCompatImageView>(R.id.ivMoreReview)
            .setOnClickListener {
                ReviewsBottomDialogFragment.newInstance(viewModel.movieDetailsMode.value?.movieReviews as ArrayList<MovieReviewResult>)
                    .show(requireActivity().supportFragmentManager, tag)
            }


        val llReviewBox =
            binding.llMovieDetailContent.findViewById<ConstraintLayout>(R.id.llReviewBox)
        val lblMoreLess = llReviewBox.findViewById<AppCompatTextView>(R.id.lblMoreLess)
        lblMoreLess.tag = false
        lblMoreLess.setOnClickListener {
            val isMore = it.tag as Boolean
            llReviewBox.findViewById<AppCompatTextView>(R.id.tvReview).maxLines =
                if (isMore) 7 else Int.MAX_VALUE
            lblMoreLess.text =
                if (isMore) it.context.getString(R.string.lbl_more) else it.context.getString(R.string.lbl_less)
            lblMoreLess.tag = !isMore
        }

        initRecyclerViews()
    }

    private fun initRecyclerViews() {

        val mCastLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        mCastLayoutManager.reverseLayout = false

        val rvCast = binding.llMovieDetailContent.findViewById<RecyclerView>(R.id.rvCast)
        mCastAdapter = CastListAdapter {
            ToastUtils.show((it.tag as MovieCast).name)
        }
        rvCast.layoutManager =
            mCastLayoutManager
        rvCast.adapter = mCastAdapter
        LinearSnapHelper().attachToRecyclerView(rvCast)

        val mProductionLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        mProductionLayoutManager.reverseLayout = false
        val rvProduction =
            binding.llMovieDetailContent.findViewById<RecyclerView>(R.id.rvProduction)
        mProductionAdapter = ProductionAdapter {
            ToastUtils.show((it.tag as ProductionCompany).name)
        }
        rvProduction.layoutManager =
            mProductionLayoutManager
        rvProduction.adapter = mProductionAdapter
        LinearSnapHelper().attachToRecyclerView(rvProduction)


        val mSimilarMovieLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        mSimilarMovieLayoutManager.reverseLayout = false
        val rvSimilarMovies =
            binding.llMovieDetailContent.findViewById<RecyclerView>(R.id.rvSimilarMovies)
        mSimilarMoviesAdapter = SimilarMoviesAdapter {
            val directions =
                MovieDetailFragmentDirections.actionToMovieDetail(it.tag as MovieResults)
            findNavController().navigate(directions)
        }
        rvSimilarMovies.layoutManager =
            mSimilarMovieLayoutManager
        rvSimilarMovies.adapter = mSimilarMoviesAdapter
        LinearSnapHelper().attachToRecyclerView(rvSimilarMovies)


    }

    override fun initObservers() {
        super.initObservers()
        viewModel.isViewLoading.observe(viewLifecycleOwner, Observer {
            if (it) binding.progressbar.show()
            else binding.progressbar.hide()
        })

        observe(viewModel.movieDetailsMode, ::handleMovieDetailResponse)

        viewModel.onMessageError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.isEmptyCastList.observe(viewLifecycleOwner, Observer {
            binding.llMovieDetailContent.findViewById<AppCompatTextView>(R.id.lblCast)
                .makeVisible(!it)
        })

        viewModel.isEmptySimilarMoviesList.observe(viewLifecycleOwner, Observer {
            binding.llMovieDetailContent.findViewById<AppCompatTextView>(R.id.lblSimilarMovies)
                .makeVisible(!it)
        })

        viewModel.isEmptyProductionList.observe(viewLifecycleOwner, Observer {
            binding.llMovieDetailContent.findViewById<AppCompatTextView>(R.id.lblProduction)
                .makeVisible(!it)
        })

        viewModel.isEmptyReviewsList.observe(viewLifecycleOwner, Observer {
            binding.llMovieDetailContent.findViewById<ConstraintLayout>(R.id.llReviewBox)
                .makeVisible(!it)
            binding.llMovieDetailContent.findViewById<AppCompatTextView>(R.id.lblReview)
                .makeVisible(!it)
            binding.llMovieDetailContent.findViewById<AppCompatImageView>(R.id.ivMoreReview)
                .makeVisible(!it)
        })


        observe(viewModel.isFinancialNegative, ::handleNegativeFinancial)
    }

    private fun handleMovieDetailResponse(it: MovieDetailsModel) {
        binding.movieDetailModel = it
        val llReviewBox =
            binding.llMovieDetailContent.findViewById<ConstraintLayout>(R.id.llReviewBox)
        if (!it.movieReviews.isNullOrEmpty()) {
            llReviewBox.findViewById<AppCompatTextView>(R.id.tvReview).text =
                it.movieReviews.first().content

            llReviewBox.findViewById<AppCompatImageView>(R.id.ivReviewerImage)
                .loadImage(
                    it.movieReviews.first().authorDetails?.avatarPath
                )
        }

        mCastAdapter?.addAll(it.movieCast)
        mProductionAdapter?.addAll(it.movieSynopsis?.productionCompanies ?: emptyList())
        mSimilarMoviesAdapter?.addAll(it.similarMoviesResult)
    }

    private fun handleNegativeFinancial(isNegativeFinancial: Boolean) {
        binding.llMovieDetailContent.findViewById<AppCompatImageView>(R.id.ivProfitLoss).apply {
            if (isNegativeFinancial) {
                loadImage(R.drawable.ic_loss)
                setColorFilter(
                    ContextCompat.getColor(context, R.color.red),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            } else {
                loadImage(R.drawable.ic_profit)
                setColorFilter(
                    ContextCompat.getColor(context, R.color.green),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
        }

    }

}
