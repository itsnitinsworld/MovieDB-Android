package com.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.R
import com.themoviedb.databinding.RowCastBinding
import com.themoviedb.model.MovieResults
import com.themoviedb.utils.extensions.loadImage
import com.themoviedb.utils.extensions.makeVisible

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */
class SimilarMoviesAdapter(
    private var callBacks: (view: View) -> Unit
) : RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {
    var items: List<MovieResults> = emptyList()
    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(items: List<MovieResults>) {
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
                R.layout.row_cast,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

    }

    inner class ViewHolder(private val binding: RowCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieResults) {
            binding.root.tag = model
            binding.root.setOnClickListener {
                callBacks.invoke(it)
            }

            binding.textView.makeVisible(false)
            binding.imageView.loadImage(model.posterPath)
        }
    }

}