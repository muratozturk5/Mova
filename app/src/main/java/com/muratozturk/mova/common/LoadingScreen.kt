package com.muratozturk.mova.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.muratozturk.mova.R


object LoadingScreen {
    var dialog: Dialog? = null
    fun displayLoadingWithText(
        context: Context?,
        text: String?,
        cancelable: Boolean
    ) {
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_loading_screen)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(cancelable)
        val textView = dialog!!.findViewById<TextView>(R.id.text)
        textView.text = text
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    fun displayLoading(
        context: Context?,
        cancelable: Boolean
    ) {
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_loading_screen)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(cancelable)
        val textView = dialog!!.findViewById<TextView>(R.id.text)
        textView.visibility = android.view.View.GONE
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}