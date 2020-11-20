package com.themoviedb.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.themoviedb.R
import com.themoviedb.adapter.ReviewsAdapter
import com.themoviedb.databinding.DialogReviewsBinding
import com.themoviedb.model.MovieReviewResult

/**
 * @author- Nitin Khanna
 * @date -
 */
class ReviewsBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogReviewsBinding
    private val reviewsList: ArrayList<MovieReviewResult> by lazy {
        requireArguments().getParcelableArrayList<MovieReviewResult>(REVIEW_LIST) as ArrayList<MovieReviewResult>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_reviews, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        // We can have cross button on the top right corner for providing elemnet to dismiss the bottom sheet
        //iv_close.setOnClickListener { dismissAllowingStateLoss() }
        binding.ivClose.setOnClickListener {
            dismissAllowingStateLoss()
        }

        val layoutManager =
            LinearLayoutManager(context)
        layoutManager.reverseLayout = false
        val mAdapter = ReviewsAdapter()
        binding.recyclerView.layoutManager =
            layoutManager
        binding.recyclerView.adapter = mAdapter
        mAdapter.addAll(reviewsList)


    }

    companion object {
        private val REVIEW_LIST = "review_list"

        @JvmStatic
        fun newInstance(reviewsList: ArrayList<MovieReviewResult>): ReviewsBottomDialogFragment {
            val fragment = ReviewsBottomDialogFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(REVIEW_LIST, reviewsList)
            fragment.arguments = bundle
            return fragment
        }
    }
}