package com.example.worldexplorer.ui.detailcountries.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldexplorer.databinding.ItemDetailCountriesBinding


class CountriesDetailViewHolder(view: View) : ViewHolder(view) {
    val binding = ItemDetailCountriesBinding.bind(view)
    fun render( item: String) {
        Log.d("Oscar Final", item)
        binding.ivBorderCountryFlag.load("https://flagcdn.com/w320/${item.lowercase()}.png")
    }
}