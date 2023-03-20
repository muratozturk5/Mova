package com.muratozturk.mova.ui.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.ui.details.images.ImagesFragment
import com.muratozturk.mova.ui.details.similar.SimilarFragment
import com.muratozturk.mova.ui.details.trailers.TrailersFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val id: Int,
    private val mediaType: MediaTypeEnum
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TrailersFragment.createBundle(id, mediaType)
            1 -> return ImagesFragment.createBundle(id, mediaType)
            2 -> return SimilarFragment.createBundle(id, mediaType)
        }
        return TrailersFragment.createBundle(id, mediaType)
    }
}