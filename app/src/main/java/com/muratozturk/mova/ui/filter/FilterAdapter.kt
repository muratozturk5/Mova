package com.muratozturk.mova.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.data.model.remote.genres.Genre
import com.muratozturk.mova.databinding.ItemGenreBinding

class FilterAdapter(private val filterResult: FilterResult) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    var filterResultHigh: (FilterResult) -> Unit = { filterResult }

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre) {
            with(binding) {
                genreCheckBox.isChecked = filterResult.selectedGenreList.contains(item)
                genreCheckBox.text = item.name

                // Used setOnClickListener instead of setOnCheckedChangeListener  because checkbox is unchecked when scrolling
                genreCheckBox.setOnClickListener {
                    if (genreCheckBox.isChecked) {
                        filterResult.selectedGenreList.add(item)
                    } else {
                        filterResult.selectedGenreList.remove(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterResult.genreList[position])
    }

    override fun getItemCount(): Int {
        return filterResult.genreList.size
    }


}