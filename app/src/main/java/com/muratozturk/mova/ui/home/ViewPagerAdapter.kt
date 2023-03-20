package com.muratozturk.mova.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.common.loadImage
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.databinding.ItemViewPagerBinding
import com.muratozturk.mova.domain.model.MovieUI

class ViewPagerAdapter(
    private val itemList: ArrayList<MovieUI>,
    private val onClickMovie: ((movieId: Int) -> Unit)?,
    private val onClickPlay: ((movieId: Int) -> Unit)?,
    private val onClickAddList: ((movieId: Int, isBookmarked: Boolean, bookmark: Bookmark) -> Unit)?
) : PagerAdapter() {

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {

        val itemBinding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        with(itemBinding) {
            with(itemList[position]) {

                backDrop.loadImage(backdropPath, imageTypeEnum = ImageTypeEnum.BACKDROP)

                titleTv.text = title
                backDrop.setOnClickListener {
                    onClickMovie?.invoke(id)
                }
                playBtn.setOnClickListener {
                    onClickPlay?.invoke(id)
                }
                addListBtn.isChecked = isBookmarked

                addListBtn.setOnClickListener {
                    onClickAddList?.invoke(
                        id, isBookmarked,
                        Bookmark(id, title, "", posterPath ?: "", voteAverage, MediaTypeEnum.MOVIE)
                    )
                }
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