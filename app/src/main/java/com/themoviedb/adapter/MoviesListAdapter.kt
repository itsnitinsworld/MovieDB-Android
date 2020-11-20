package com.themoviedb.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.R
import com.themoviedb.databinding.RowMovieBinding
import com.themoviedb.ui.fragment.movielist.model.MovieResults
import com.themoviedb.utils.extensions.loadImage

class MoviesListAdapter(
    private var callBacks: (view: View) -> Unit
) : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {
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
                R.layout.row_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

    }

    inner class ViewHolder(private val binding: RowMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieResults) {
            binding.model = model
            binding.root.tag = model
            binding.root.setOnClickListener {
                callBacks.invoke(it)
            }

            binding.rating.rating = model.voteAverage.toFloat() / 2
            binding.ivBanner.loadImage(model.posterPath)
        }
    }

}