package com.example.worldexplorer.ui.countries.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ItemCountriesBinding
import com.example.worldexplorer.domain.models.countries.CountriesModel


class CountriesViewHolder(view: View) : ViewHolder(view) {
    val binding = ItemCountriesBinding.bind(view)
    fun render(
        context: Context?,
        item: CountriesModel,
        onItemSelected: (CountriesModel, ImageView, TextView) -> Unit
    ) {
        binding.apply {
            tvCountryName.text = item.name
            setBackgroundColor(item, context!!)

            ivCountryFlag.transitionName = item.cca2
            tvCountryName.transitionName = item.name

            cvItem.setOnClickListener {
                onItemSelected(
                    item,
                    binding.ivCountryFlag,
                    binding.tvCountryName
                )
            }
        }

    }

    private fun setBackgroundColor(item: CountriesModel, context: Context) {
        binding.ivCountryFlag.load("https://flagcdn.com/w320/${item.cca2.lowercase()}.png") {
            // Disable hardware bitmaps as Palette needs to read the image's pixels.
            allowHardware(false)
            listener(
                onSuccess = { _, result ->
                    // Create the palette on a background thread.
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        // Consume the palette.
                        val drawable = GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            palette?.dominantSwatch?.let {
                                intArrayOf(
                                    it.rgb,
                                    ContextCompat.getColor(context, R.color.accent),
                                )
                            }
                        )
                        binding.clItem.background = drawable
                    }
                }
            )
        }
    }

}