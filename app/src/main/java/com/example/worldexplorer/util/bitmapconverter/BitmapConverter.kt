package com.example.worldexplorer.util.bitmapconverter

import android.graphics.Bitmap

interface BitmapConverter {

    suspend fun getBitmapFromUrl(imageUrl: String): Bitmap

}