package com.example.worldexplorer.ui.countries.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.databinding.ItemCountriesBinding
import com.example.worldexplorer.domain.models.countries.CountriesModel

class CountriesViewHolder(view: View): ViewHolder(view) {
    val binding = ItemCountriesBinding.bind(view)
    fun render(item: CountriesModel, onItemSelected: (CountriesModel) -> Unit) {
        binding.tvCountryName.text = item.name
        binding.ivCountryFlag.load(item.flagImageUrl)

        binding.parent.setOnClickListener { onItemSelected(item) }
    }

}