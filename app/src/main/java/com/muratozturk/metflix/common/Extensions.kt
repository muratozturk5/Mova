package com.muratozturk.metflix.common

import android.app.Activity
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.muratozturk.metflix.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


fun EditText.changeFocusedInputTint(isFocused: Boolean) {

    if (isFocused) {

        for (drawable in this.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        androidx.core.content.ContextCompat.getColor(
                            this.context,
                            R.color.text_color
                        ),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
            }
        }

    } else {
        if (this.text.toString().isEmpty()) {

            for (drawable in this.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter =
                        PorterDuffColorFilter(
                            androidx.core.content.ContextCompat.getColor(
                                this.context,
                                R.color.inactive_input
                            ),
                            android.graphics.PorterDuff.Mode.SRC_IN
                        )
                }
            }
        }


    }

}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Activity.showToast(
    title: String?,
    description: String,
    style: MotionToastStyle
) {
    MotionToast.createColorToast(
        this,
        title,
        description,
        style,
        MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.urbanist_font_family)
    )
}

