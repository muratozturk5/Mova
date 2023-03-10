package com.muratozturk.metflix.ui.download

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.metflix.common.convertMBtoGB
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.enums.MediaTypeEnum
import com.muratozturk.metflix.common.formatTime
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.data.model.local.Download
import com.muratozturk.metflix.databinding.ItemDownloadBinding

class DownloadAdapter(private val downloadList: ArrayList<Download>) :
    RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {
    var onClickHigh: (Int, MediaTypeEnum) -> Unit = { _, _ -> }
    var onClickRemoveHigh: (Download, Int) -> Unit = { _, _ -> }

    inner class ViewHolder(private val binding: ItemDownloadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Download, position: Int) {
            with(binding) {

                sizeTV.text = item.downloadSize.convertMBtoGB(true)
                titleTv.text = item.name
                runtimeTv.text = item.runtime.formatTime()
                deleteBtn.setOnClickListener {
                    onClickRemoveHigh(item, position)
                }
                imageView.loadImage(item.backdrop, imageTypeEnum = ImageTypeEnum.BACKDROP)
                cardView.setOnClickListener {
                    onClickHigh(item.id, item.type)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDownloadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(downloadList[position], position)
    }

    override fun getItemCount(): Int {
        return downloadList.size
    }

    fun removeItem(position: Int) {
        downloadList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(
            position,
            itemCount
        )
    }

}