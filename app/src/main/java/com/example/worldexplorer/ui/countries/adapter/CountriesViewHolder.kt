package com.example.worldexplorer.ui.countries.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.databinding.ItemCountriesBinding
import com.example.worldexplorer.domain.models.countries.CountriesModel

class CountriesViewHolder(view: View) : ViewHolder(view) {
    val binding = ItemCountriesBinding.bind(view)
    fun render(
        item: CountriesModel,
        onItemSelected: (CountriesModel, ImageView, TextView) -> Unit
    ) {
        binding.apply {
            tvCountryName.text = item.name
            ivCountryFlag.load("https://flagcdn.com/w320/${item.cca2.lowercase()}.png")

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