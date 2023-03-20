package com.muratozturk.mova.ui.profile.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.loadImage
import com.muratozturk.mova.databinding.ItemLanguageBinding
import com.muratozturk.mova.domain.model.LanguageUI


class LanguagesAdapter(
    private val list: List<LanguageUI>,
    private var currentLanguageCode: String
) :
    RecyclerView.Adapter<LanguagesAdapter.ViewHolder>() {
    var onClick: (String, String) -> Unit = { _, _ -> }
    private var lastCheckedPosition = -1

    inner class ViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LanguageUI, position: Int) {
            with(binding) {

                flagIv.loadImage(item.iso6391, imageTypeEnum = ImageTypeEnum.FLAG)
                languageBtn.text = item.englishName

                root.setOnClickListener {
                    val copyOfLastCheckedPosition: Int = lastCheckedPosition
                    lastCheckedPosition = position
                    notifyItemChanged(copyOfLastCheckedPosition)
                    notifyItemChanged(lastCheckedPosition)

                    currentLanguageCode = item.iso6391
                    onClick(item.englishName, item.iso6391)
                }

                if (currentLanguageCode == item.iso6391) {
                    lastCheckedPosition = position
                } else {
                    languageBtn.isChecked = false
                }

                languageBtn.isChecked = position == lastCheckedPosition

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}