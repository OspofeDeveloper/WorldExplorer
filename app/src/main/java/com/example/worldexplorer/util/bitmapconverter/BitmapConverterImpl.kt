package com.example.worldexplorer.util.bitmapconverter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BitmapConverterImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BitmapConverter {

    override suspend fun getBitmapFromUrl(imageUrl: String): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable

        return (result as BitmapDrawable).bitmap
    }

}
