package com.muratozturk.mova.ui.dialog.download

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.mova.R
import com.muratozturk.mova.common.convertMBtoGB
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.FragmentDownloadDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class DownloadDialogFragment : BottomSheetDialogFragment(R.layout.fragment_download_dialog) {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.AppModalStyle)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        dialog?.setCancelable(false)
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
    }

    private val binding by viewBinding(FragmentDownloadDialogBinding::bind)
    private val viewModel by viewModels<DownloadDialogViewModel>()
    private val args: DownloadDialogFragmentArgs by navArgs()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var progressBarJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    fun initUI() {
        with(binding) {
            hideButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.addDownload(args.download)
            progressBarJob = coroutineScope.launch {
                repeat(101) {
                    updateProgress(it)
                    delay(100)
                }
            }

            cancelBtn.setOnClickListener {
                progressBarJob?.cancel()
            }

        }
    }

    private fun updateProgress(progress: Int) {
        with(binding) {

            val downloadSize = args.download.downloadSize
            val downloadedSize = (progress * downloadSize) / 100.0

            val progressPercentBuilder: StringBuilder =
                StringBuilder().append(progress).append(" %")

            val progressBuilder: StringBuilder =
                StringBuilder().append(downloadedSize.convertMBtoGB(false)).append(" / ")
                    .append(downloadSize.convertMBtoGB(true))

            progressPercent.text = progressPercentBuilder.toString()
            progressText.text = progressBuilder.toString()
            progressBar.progress = progress
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        progressBarJob?.cancel()
    }
}