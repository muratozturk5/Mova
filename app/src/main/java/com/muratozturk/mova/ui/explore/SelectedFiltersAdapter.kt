package com.muratozturk.mova.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.mova.databinding.ItemGenreBinding

class SelectedFiltersAdapter(private val filterList: List<String>) :
    RecyclerView.Adapter<SelectedFiltersAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                genreCheckBox.isChecked = true
                genreCheckBox.text = item
                genreCheckBox.isClickable = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getItemCount(): Int {
        return filterList.size
    }


}