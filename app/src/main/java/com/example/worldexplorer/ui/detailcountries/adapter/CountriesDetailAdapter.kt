package com.example.worldexplorer.ui.detailcountries.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R

class CountriesDetailAdapter(
    private val borders: List<String>,
) : RecyclerView.Adapter<CountriesDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesDetailViewHolder {
        Log.d("Oscar", borders.toString())
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountriesDetailViewHolder(layoutInflater.inflate(R.layout.item_detail_countries, parent, false))
    }

    override fun getItemCount(): Int = borders.size

    override fun onBindViewHolder(holder: CountriesDetailViewHolder, position: Int) {
        val item = borders[position]
        holder.render(item)
    }
}