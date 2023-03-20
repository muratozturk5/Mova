package com.muratozturk.mova.ui.person.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.loadImage
import com.muratozturk.mova.databinding.ItemPersonImageBinding
import com.muratozturk.mova.domain.model.ImageUI

class PersonImagesAdapter(private val list: List<ImageUI>) :
    RecyclerView.Adapter<PersonImagesAdapter.ViewHolder>() {
    var onClick: (List<ImageUI>, Int) -> Unit = { _, _ -> }

    inner class ViewHolder(private val binding: ItemPersonImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageUI, position: Int) {
            with(binding) {
                imageView.loadImage(item.filePath, imageTypeEnum = ImageTypeEnum.POSTER)

                root.setOnClickListener {
                    onClick(list, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPersonImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}