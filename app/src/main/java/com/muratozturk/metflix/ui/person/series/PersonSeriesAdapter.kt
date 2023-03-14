package com.muratozturk.metflix.ui.person.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.format
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemMovieSerieNowPlayingBinding
import com.muratozturk.metflix.domain.model.SerieUI

class PersonSeriesAdapter(private val list: List<SerieUI>) :
    RecyclerView.Adapter<PersonSeriesAdapter.ViewHolder>() {
    var onClick: (Int) -> Unit = {}


    inner class ViewHolder(private val binding: ItemMovieSerieNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SerieUI) {
            with(binding) {
                item.posterPath?.let {
                    imageView.loadImage(
                        it,
                        imageTypeEnum = ImageTypeEnum.POSTER
                    )
                }
                voteAverageTV.text = item.voteAverage.format(1)
                root.setOnClickListener {
                    onClick.invoke(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieSerieNowPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}