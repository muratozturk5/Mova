package com.muratozturk.metflix.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager

/**
 * Created by luis rafael on 16/02/19.
 */
class CustomViewPagerScroll : ViewPager {

    constructor(context: Context) : super(context) {
        postInitViewPager()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        postInitViewPager()
    }

    private var mScroller: ScrollerCustomDuration? = null

    private fun postInitViewPager() {
        try {
            val viewpager = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            val interpolator = viewpager.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true

            mScroller = ScrollerCustomDuration(context, interpolator.get(null) as Interpolator)
            scroller.set(this, mScroller)
        } catch (e: Exception) {
        }

    }


    fun setScrollDurationFactor(scrollFactor: Int) {
        mScroller!!.setScrollDurationFactor(scrollFactor)
    }

    inner class ScrollerCustomDuration(context: Context, interpolator: Interpolator) :
        Scroller(context, interpolator) {

        private var mScrollFactor = 1

        fun setScrollDurationFactor(scrollFactor: Int) {
            mScrollFactor = scrollFactor
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, (duration * mScrollFactor))
        }

    }

}

fun parallaxPageTransformer(vararg v: Int): ViewPager.PageTransformer =
    ViewPager.PageTransformer { page, position ->
        for (i in 0 until v.size) {
            val view = page.findViewById<View>(v[i])
            if (view != null) ViewCompat.setTranslationX(
                view,
                (page.width / 1.5 * position).toFloat()
            )
        }
    }