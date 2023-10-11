package com.example.worldexplorer.ui.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.domain.countries.model.CountryItem

class CountriesAdapter(private val list: List<CountryItem>) : RecyclerView.Adapter<CountrieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountrieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountrieViewHolder(layoutInflater.inflate(R.layout.item_list_countries, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CountrieViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }
}