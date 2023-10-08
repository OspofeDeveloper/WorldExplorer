package com.example.worldexplorer.ui.listCountries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R

class ListCountriesAdapter(private val list: List<CountriesModel>) : RecyclerView.Adapter<ListCountrieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCountrieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListCountrieViewHolder(layoutInflater.inflate(R.layout.item_list_countries, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListCountrieViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }
}