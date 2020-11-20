package com.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.R
import com.themoviedb.databinding.RowImageBinding
import com.themoviedb.model.ProductionCompany
import com.themoviedb.utils.extensions.loadImage

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */
class ProductionAdapter(
    private var callBacks: (view: View) -> Unit
) : RecyclerView.Adapter<ProductionAdapter.ViewHolder>() {
    var items: List<ProductionCompany> = emptyList()
    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(items: List<ProductionCompany>) {
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
                R.layout.row_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

    }

    inner class ViewHolder(private val binding: RowImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProductionCompany) {
            binding.root.tag = model
            binding.root.setOnLongClickListener {
                callBacks.invoke(it)
                false
            }

            binding.imageView.loadImage(model.logoPath)
        }
    }

}