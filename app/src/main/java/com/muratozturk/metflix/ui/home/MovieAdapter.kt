package com.muratozturk.metflix.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.moviehouse.common.base.BasePagingAdapter
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemMovieSerieBinding
import com.muratozturk.metflix.domain.model.MovieUI

class MovieAdapter(
    private val onClickMovie: ((movieId: Int) -> Unit)?
) : BasePagingAdapter<MovieUI>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    class MovieViewHolder(
        private val binding: ItemMovieSerieBinding,
        private val onClickMovie: ((movieId: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieUI) = binding.apply {

            imageView.loadImage(item.posterPath, imageTypeEnum = ImageTypeEnum.POSTER)
            voteAverageTV.text = item.voteAverage.toString()

            root.setOnClickListener {
                onClickMovie?.invoke(item.id)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        MovieViewHolder(
            ItemMovieSerieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickMovie
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                getItem(position)?.let { movie -> holder.bind(movie) }
            }
        }
    }


}