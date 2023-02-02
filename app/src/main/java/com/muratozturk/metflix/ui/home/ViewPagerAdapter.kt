package com.muratozturk.metflix.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.muratozturk.metflix.common.loadImage
import com.muratozturk.metflix.databinding.ItemViewPagerBinding
import com.muratozturk.metflix.domain.model.MovieUI

class ViewPagerAdapter(private val itemList: ArrayList<MovieUI>) : PagerAdapter() {

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {

        val itemBinding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        with(itemBinding) {
            with(itemList[position]) {
                if (backdropPath != null) {
                    backDrop.loadImage(backdropPath, isPoster = false)
                }
                titleTv.text = title
//                genresTv.text =

            }
        }


        parent.addView(itemBinding.root, 0)

        return itemBinding.root
    }

    override fun getCount(): Int = itemList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == (`object` as ConstraintLayout)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.removeView(`object` as ConstraintLayout)
    }
}