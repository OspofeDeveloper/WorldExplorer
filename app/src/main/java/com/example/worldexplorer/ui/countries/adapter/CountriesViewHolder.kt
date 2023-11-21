package com.example.worldexplorer.ui.countries.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.databinding.ItemCountriesBinding
import com.example.worldexplorer.domain.models.countries.CountryBasicModel


class CountriesViewHolder(
    view: View
) : ViewHolder(view) {
    val binding = ItemCountriesBinding.bind(view)
    fun render(
        item: CountryBasicModel,
        onItemSelected: (CountryBasicModel, ImageView, TextView) -> Unit
    ) {
        binding.apply {
            tvCountryName.text = item.name
            ivCountryFlag.load(item.imageUrl)
            clItem.background = item.backgroundDrawable

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
}