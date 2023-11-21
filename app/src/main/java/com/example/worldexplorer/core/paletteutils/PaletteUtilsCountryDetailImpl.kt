package com.example.worldexplorer.core.paletteutils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.example.worldexplorer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PaletteUtilsCountryDetailImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PaletteUtils {

    /** En este caso cargamos el palette de forma sincrona usando Palette.from(builder) aunque lo
     *  recomendado es hacerlo de forma asíncrona, es decir, en un hilo fuera del hilo principal,
     *  que se haria usando Palette.Build(bitmap).generate { palett -> ... }.
     *  La razón porque uso la forma sincrona es porque lo hago dentro de una corrutina, de tal
     *  forma que lo estoy haciendo fuera del hilo principal.*/
    override suspend fun getBackgroundGradient(bitmap: Bitmap): GradientDrawable {
        val drawable: GradientDrawable

        Palette.from(bitmap).generate().apply {
            drawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                dominantSwatch?.let {
                    intArrayOf(
                        ContextCompat.getColor(context, R.color.primary),
                        it.rgb,
                        ContextCompat.getColor(context, R.color.accent),
                    )
                }
            )
        }

        return drawable
    }

}