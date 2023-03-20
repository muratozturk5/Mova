package com.muratozturk.mova.ui.details.trailers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.getReformatDate
import com.muratozturk.mova.common.loadImage
import com.muratozturk.mova.databinding.ItemTrailerBinding
import com.muratozturk.mova.domain.model.VideoUI

class TrailersAdapter(
    private val trailerList: List<VideoUI>
) :
    RecyclerView.Adapter<TrailersAdapter.ViewHolder>() {
    var onClick: (String) -> Unit = {}

    inner class ViewHolder(private val binding: ItemTrailerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoUI) {
            with(binding) {
                titleTv.text = item.name
                dateTv.text = getReformatDate(item.publishedAt)
                imageView.loadImage(item.key, imageTypeEnum = ImageTypeEnum.YOUTUBE)

                root.setOnClickListener {
                    onClick(item.key)
                }
                cardView.setOnClickListener {
                    onClick(item.key)
                }
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