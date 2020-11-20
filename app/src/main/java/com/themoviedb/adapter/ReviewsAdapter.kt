package com.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.R
import com.themoviedb.databinding.RowReviewBinding
import com.themoviedb.model.MovieReviewResult
import com.themoviedb.utils.extensions.loadImage

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */
class ReviewsAdapter() : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    var items: List<MovieReviewResult> = emptyList()
    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(items: List<MovieReviewResult>) {
        if (this.items != items) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_review,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

    }

    inner class ViewHolder(private val binding: RowReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieReviewResult) {
            binding.root.tag = model

            binding.lblMoreLess.tag = false

            binding.lblMoreLess.setOnClickListener {
                val isMore = it.tag as Boolean
                binding.tvReview.maxLines = if (isMore) 7 else Int.MAX_VALUE
                binding.lblMoreLess.text =
                    if (isMore) it.context.getString(R.string.lbl_more) else it.context.getString(R.string.lbl_less)
                binding.lblMoreLess.tag = !isMore
            }

            binding.tvReview.text = model.content
            binding.ivReviewerImage.loadImage(model.authorDetails?.avatarPath)
        }
    }

}