package com.muratozturk.metflix.ui.details.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemImageBinding
import com.muratozturk.metflix.domain.model.ImageUI

class ImagesAdapter(private val trailerList: List<ImageUI>) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    var onClick: (List<ImageUI>, Int) -> Unit = { _, _ -> }

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageUI, position: Int) {
            with(binding) {
                imageView.loadImage(item.filePath, imageTypeEnum = ImageTypeEnum.BACKDROP)
                root.setOnClickListener {
                    onClick(trailerList, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trailerList[position], position)
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }


}