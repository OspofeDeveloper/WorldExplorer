package com.example.worldexplorer.ui.detailcountries.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.load
import com.example.worldexplorer.databinding.ItemDetailCountriesBinding

class CountriesDetailAdapter(
    private val context: Context,
    private val borderNames: List<String>,
    private val borderCca2: List<String>
) : BaseAdapter() {

    override fun getCount(): Int = borderNames.size
    override fun getItem(position: Int): Any? = null
    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemDetailCountriesBinding

        if (convertView == null) {
            binding = ItemDetailCountriesBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemDetailCountriesBinding
        }

        binding.ivBorderCountryFlag.load("https://flagcdn.com/w320/${borderCca2[position].lowercase()}.png")
        binding.tvCountryName.text = borderNames[position]

        return binding.root
    }

}