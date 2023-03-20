package com.muratozturk.mova.ui.home.now_playing_series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.common.base.BasePagingAdapter
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.format
import com.muratozturk.mova.common.loadImage
import com.muratozturk.mova.databinding.ItemMovieSerieNowPlayingBinding
import com.muratozturk.mova.domain.model.SerieUI

class NowPlayingSeriesAdapter(
    private val onClick: ((id: Int) -> Unit)?
) : BasePagingAdapter<SerieUI>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    class MovieViewHolder(
        private val binding: ItemMovieSerieNowPlayingBinding,
        private val onClick: ((id: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SerieUI) = binding.apply {

            imageView.loadImage(item.posterPath, imageTypeEnum = ImageTypeEnum.POSTER)
            voteAverageTV.text = item.voteAverage.format(1)
            root.setOnClickListener {
                onClick?.invoke(item.id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        MovieViewHolder(
            ItemMovieSerieNowPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                getItem(position)?.let { movie -> holder.bind(movie) }
            }
        }
    }


}