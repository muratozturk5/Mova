package com.muratozturk.metflix.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.Constants.getBackDropPath
import com.muratozturk.metflix.common.Constants.getPosterPath
import com.muratozturk.metflix.common.Constants.getYouTubePath
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


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

fun getRandomRuntime(): Int {
    return Random.nextInt(90, 180)
}

fun getRandomDownloadSize(): Double {
    return Random.nextDouble(500.0, 3000.0)
}

fun Double.convertMBtoGB(addText: Boolean): String {
    if (this >= 1024.0) {
        return "${(this / 1024.0).format(1)} ${if (addText) " GB" else ""}"
    }

    return "${this.format(1)} ${if (addText) " MB" else ""}"
}

fun Int.formatTime(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return String.format("%01dh %02dm", hours, remainingMinutes)
}

fun Double.format(digits: Int): String {
    val df = DecimalFormat()
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    df.maximumFractionDigits = digits
    return df.format(this)
}

fun getReformatDate(dateInString: String?): String {

    return if (dateInString != null) {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        try {
            val date = parser.parse(dateInString)
            formatter.format(date)
        } catch (e: ParseException) {
            "-"
        }
    } else "-"
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

fun ImageView.loadImage(url: String?, isBlur: Boolean? = false, imageTypeEnum: ImageTypeEnum) {

    val placeholder = when (imageTypeEnum) {
        ImageTypeEnum.BACKDROP -> R.drawable.gray_placeholder
        ImageTypeEnum.POSTER -> R.drawable.gray_placeholder
        ImageTypeEnum.YOUTUBE -> R.drawable.gray_placeholder
        ImageTypeEnum.CREDIT -> R.drawable.profile_filled
        ImageTypeEnum.LOCAL -> R.drawable.gray_placeholder
    }

    url?.let {

        val urlString = when (imageTypeEnum) {
            ImageTypeEnum.BACKDROP -> getBackDropPath(url)
            ImageTypeEnum.POSTER -> getPosterPath(url)
            ImageTypeEnum.YOUTUBE -> getYouTubePath(url)
            ImageTypeEnum.CREDIT -> getPosterPath(url)
            ImageTypeEnum.LOCAL -> url
        }

        if (isBlur == true) {
            Glide.with(this.context)
                .load(urlString)
                .apply(RequestOptions())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 1)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(this.context.circularProgressDrawable())
                .error(placeholder)
                .into(this)
        } else {
            Glide.with(this.context)
                .load(urlString)
                .apply(RequestOptions())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(this.context.circularProgressDrawable())
                .error(placeholder)
                .into(this)
        }

    } ?: run {
        this.setImageResource(placeholder)
    }

}


fun Context.openShareIntent(text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(
        Intent.createChooser(intent, getString(R.string.share))
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}

suspend fun Context.imageDownloadSaveFile(photoName: String, url: String): String {
    try {
        val image = File(filesDir, photoName)
        val imageUri = FileProvider.getUriForFile(
            this,
            "com.muratozturk.metflix.fileProvider",
            image
        ).toString()

        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        val outputStream =
            this.contentResolver.openOutputStream(Uri.parse(imageUri))
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        withContext(Dispatchers.IO) {
            outputStream!!.close()
        }
        return imageUri
    } catch (e: Exception) {
        Timber.tag("getBitmap-Ex").e(e.toString())

        return ""
    }

}