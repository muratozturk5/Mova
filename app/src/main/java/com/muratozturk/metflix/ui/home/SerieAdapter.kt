package com.muratozturk.metflix.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.moviehouse.common.base.BasePagingAdapter
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemMovieSerieBinding
import com.muratozturk.metflix.domain.model.SerieUI


class SerieAdapter(
    private val onClick: ((id: Int) -> Unit)?
) : BasePagingAdapter<SerieUI>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    class SerieViewHolder(
        private val binding: ItemMovieSerieBinding,
        private val onClick: ((id: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SerieUI) = binding.apply {

            item.posterPath?.let { imageView.loadImage(it, isPoster = true) }
            voteAverageTV.text = item.voteAverage.toString()

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
        SerieViewHolder(
            ItemMovieSerieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SerieViewHolder -> {
                getItem(position)?.let { movie -> holder.bind(movie) }
            }
        }
    }


}