package com.soft.centralcurrency.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.util.*

object BindingUtil {

    @JvmStatic
    @BindingAdapter("app:image")
    fun setImage(image: ImageView, name: String) {
        name.toLowerCase(Locale.ENGLISH).also {
            val resources = image.context.resources
            val resourceId = resources.getIdentifier(it,"drawable", image.context.packageName)
            if (resourceId > 0) {
                image.setImageDrawable(resources.getDrawable(resourceId, null))
            }
        }
    }

}