package com.example.worldexplorer.util.paletteutils

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable

interface PaletteUtils {

    suspend fun getBackgroundGradient(bitmap: Bitmap) : GradientDrawable
}