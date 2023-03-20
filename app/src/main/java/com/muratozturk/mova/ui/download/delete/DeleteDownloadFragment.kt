package com.muratozturk.mova.ui.download.delete

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.databinding.FragmentDeleteDownloadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteDownloadFragment : BottomSheetDialogFragment(R.layout.fragment_delete_download) {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        context?.let {
            if (window != null) {
                window.statusBarColor = it.getColor(android.R.color.transparent)
            }
        }
        if (dialog is BottomSheetDialog) {
            val behaviour = (dialog as BottomSheetDialog).behavior
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.skipCollapsed = true

        }
    }

    private val binding by viewBinding(FragmentDeleteDownloadBinding::bind)
    private val viewModel: DeleteDownloadViewModel by viewModels()
    private val args: DeleteDownloadFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    fun initUI() {
        with(binding) {
            with(args) {

                imageView.loadImage(download.backdrop, imageTypeEnum = ImageTypeEnum.BACKDROP)
                sizeTV.text = download.downloadSize.convertMBtoGB(true)
                titleTv.text = download.name
                runtimeTv.text = download.runtime.formatTime()

                cancelBtn.setOnClickListener {
                    findNavController().popBackStack()
                }
                deleteButton.setOnClickListener {
                    viewModel.removeDownloaded(args.download.id)
                    setFragmentResult(
                        Constants.Arguments.POP_UP,
                        bundleOf(Constants.Arguments.DELETED_POSITION to args.position)
                    )
                    findNavController().popBackStack()
                }
            }
        }

    }
}