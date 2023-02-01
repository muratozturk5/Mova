package com.muratozturk.metflix.ui.home.now_playing_series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemMovieSerieNowPlayingBinding
import com.muratozturk.metflix.domain.model.SerieUI

class NowPlayingSeriesAdapter(private val movieList: ArrayList<SerieUI>) :
    RecyclerView.Adapter<NowPlayingSeriesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMovieSerieNowPlayingBinding) :
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
            ItemMovieSerieNowPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(movieList[position])

    override fun getItemCount(): Int = movieList.size

}