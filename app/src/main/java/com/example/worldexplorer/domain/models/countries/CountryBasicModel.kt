package com.example.worldexplorer.domain.models.countries

import android.graphics.drawable.GradientDrawable

data class CountryBasicModel(
    var cca2: String,
    var name: String,
    var imageUrl: String,
    var backgroundDrawable: GradientDrawable
)
