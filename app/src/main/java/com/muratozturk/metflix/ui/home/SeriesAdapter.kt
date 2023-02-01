package com.muratozturk.metflix.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemMovieSerieBinding
import com.muratozturk.metflix.domain.model.SerieUI

class SeriesAdapter(private val movieList: ArrayList<SerieUI>) :
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMovieSerieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SerieUI) {
            with(binding) {
                with(item) {
                    item.posterPath?.let { imageView.loadImage(it, isPoster = true) }
                    voteAverageTV.text = voteAverage.toString()
//                    root.setOnClickListener { onClick(charId) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(movieList[position])

    override fun getItemCount(): Int = movieList.size

}