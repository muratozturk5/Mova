package com.muratozturk.metflix.common

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.muratozturk.metflix.R
import jp.wasabeef.glide.transformations.BlurTransformation
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


fun Context.circularProgressDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 7f
        centerRadius = 80f
        start()
    }
}

fun ImageView.loadImage(url: String, isBlur: Boolean? = false, isPoster: Boolean) {

    val urlString = if (isPoster) {
        Constants.getPosterPath(url)
    } else {
        Constants.getBackDropPath(url)
    }
    if (isBlur == true) {
        Glide.with(this.context)
            .load(urlString)
            .override(500, 500)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 1)))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(this.context.circularProgressDrawable())
            .error(R.drawable.profile)
            .into(this)
    } else {
        Glide.with(this.context)
            .load(urlString)
            .override(500, 500)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(this.context.circularProgressDrawable())
            .error(R.drawable.profile)
            .into(this)
    }


}
