package com.example.worldexplorer.ui.countries.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.databinding.ItemListCountriesBinding
import com.example.worldexplorer.domain.countries.model.CountryItem

class CountrieViewHolder(view: View): ViewHolder(view) {
    val binding = ItemListCountriesBinding.bind(view)
    fun render(item: CountryItem) {
        binding.tvCountryName.text = item.name
        binding.ivCountryFlag.load(item.flagImageUrl)
    }

}