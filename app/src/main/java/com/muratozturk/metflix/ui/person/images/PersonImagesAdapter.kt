package com.muratozturk.metflix.ui.person.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemPersonImageBinding
import com.muratozturk.metflix.domain.model.PersonImageUI

class PersonImagesAdapter(private val list: List<PersonImageUI>) :
    RecyclerView.Adapter<PersonImagesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPersonImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PersonImageUI) {
            with(binding) {
                imageView.loadImage(item.filePath, imageTypeEnum = ImageTypeEnum.POSTER)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPersonImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}