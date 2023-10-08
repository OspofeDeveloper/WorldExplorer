package com.example.worldexplorer.ui.listCountries

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.worldexplorer.databinding.ItemListCountriesBinding

class ListCountrieViewHolder(view: View): ViewHolder(view) {
    val binding = ItemListCountriesBinding.bind(view)
    fun render(item: CountriesModel) {
        binding.tvCountryName.text = item.codeCca2
    }

}