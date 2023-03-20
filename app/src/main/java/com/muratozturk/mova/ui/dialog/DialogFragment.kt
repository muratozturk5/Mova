package com.muratozturk.mova.ui.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.mova.R
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.FragmentDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DialogFragment : BottomSheetDialogFragment(R.layout.fragment_dialog) {

    private val binding by viewBinding(FragmentDialogBinding::bind)
    private val args: DialogFragmentArgs by navArgs()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            title.text = args.dialogArgs.title
            message.text = args.dialogArgs.message
            image.setImageResource(args.dialogArgs.imageResource)

            CoroutineScope(Dispatchers.Main).launch {
                delay(5000)
                findNavController().navigate(args.dialogArgs.navigationDestination)
            }
        }
    }

}