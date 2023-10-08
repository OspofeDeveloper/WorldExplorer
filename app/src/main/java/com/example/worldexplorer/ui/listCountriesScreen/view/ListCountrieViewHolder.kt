package com.example.worldexplorer.ui.listCountriesScreen.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.databinding.ItemListCountriesBinding
import com.example.worldexplorer.domain.model.CountryItem

class ListCountrieViewHolder(view: View): ViewHolder(view) {
    val binding = ItemListCountriesBinding.bind(view)
    fun render(item: CountryItem) {
        binding.tvCountryName.text = item.name
        binding.ivCountryFlag.load(item.flagImageUrl)
    }

}