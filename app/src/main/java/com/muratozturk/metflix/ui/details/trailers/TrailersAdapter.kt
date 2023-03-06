package com.muratozturk.metflix.ui.details.trailers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.getReformatDate
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemTrailerBinding
import com.muratozturk.metflix.domain.model.VideoUI

class TrailersAdapter(private val trailerList: List<VideoUI>) :
    RecyclerView.Adapter<TrailersAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTrailerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoUI) {
            with(binding) {
                titleTv.text = item.name
                dateTv.text = getReformatDate(item.publishedAt)
                imageView.loadImage(item.key, imageTypeEnum = ImageTypeEnum.YOUTUBE)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trailerList[position])
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }


}