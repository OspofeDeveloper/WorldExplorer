package com.example.worldexplorer.ui.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.domain.models.countries.CountriesModel

class CountriesAdapter(
    private val list: List<CountriesModel>,
    private val onItemSelected: (CountriesModel, ImageView, TextView) -> Unit
) : RecyclerView.Adapter<CountriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountriesViewHolder(layoutInflater.inflate(R.layout.item_countries, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val item = list[position]
        holder.render(item, onItemSelected)
    }
}