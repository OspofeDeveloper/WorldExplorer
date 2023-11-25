package com.example.worldexplorer.util.paletteutils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.example.worldexplorer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PaletteUtilsCountryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PaletteUtils {

    override suspend fun getBackgroundGradient(bitmap: Bitmap): GradientDrawable {
        val drawable: GradientDrawable

        Palette.from(bitmap).generate().apply {
            drawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                dominantSwatch?.let {
                    intArrayOf(
                        it.rgb,
                        ContextCompat.getColor(context, R.color.accent),
                    )
                }
            )
        }

        return drawable
    }

}