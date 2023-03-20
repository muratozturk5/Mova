package com.muratozturk.mova.ui.images

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.muratozturk.mova.R
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.pageTransformer
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.FragmentPreviewImagesBinding

class PreviewImagesFragment : Fragment(R.layout.fragment_preview_images) {

    private val binding by viewBinding(FragmentPreviewImagesBinding::bind)
    private val args: PreviewImagesFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    fun initUI() {
        with(binding) {

            if (args.imageType == ImageTypeEnum.BACKDROP) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }

            closeBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            imageCounterTV.text = java.lang.StringBuilder().append(args.position + 1).append(" / ")
                .append(args.imageList.size).toString()

            val adapterViewPager = ImagesViewPagerAdapter(args.imageList)

            imagesViewPager.apply {
                setPageTransformer(true, pageTransformer())
                adapter = adapterViewPager
                setCurrentItem(args.position, true)
            }

            imagesViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {

                    imageCounterTV.text =
                        java.lang.StringBuilder().append(position + 1).append(" / ")
                            .append(args.imageList.size).toString()
                }

            })
        }
    }

}