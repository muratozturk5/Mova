package com.muratozturk.mova.ui.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.common.format
import com.muratozturk.mova.common.loadImage
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.databinding.ItemMovieSerieNowPlayingBinding

class MyListAdapter(private val bookmarkList: List<Bookmark>) :
    RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    var onClickHigh: (Int, MediaTypeEnum) -> Unit = { _, _ -> }

    inner class ViewHolder(private val binding: ItemMovieSerieNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Bookmark) {
            with(binding) {
                //use this for local image
                //imageView.loadImage(item.imageFilePath, imageTypeEnum = ImageTypeEnum.LOCAL)

                imageView.loadImage(item.poster, imageTypeEnum = ImageTypeEnum.POSTER)
                voteAverageTV.text = item.voteAverage.format(1)
                root.setOnClickListener {
                    onClickHigh(item.id, item.type)
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
        holder.bind(bookmarkList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }


}