package com.muratozturk.metflix.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemCreditBinding
import com.muratozturk.metflix.domain.model.CastUI

class CreditsAdapter(private val list: List<CastUI>) :
    RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {
    var onClickHigh: (Int) -> Unit = { }

    inner class ViewHolder(private val binding: ItemCreditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CastUI) {
            with(binding) {
                nameTv.text = item.name
                characterTv.text = item.character

                imageView.loadImage(
                    item.profilePath,
                    imageTypeEnum = ImageTypeEnum.CREDIT
                )

                root.setOnClickListener {
                    onClickHigh(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCreditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}