package com.example.worldexplorer.ui.detailcountries.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.Coil
import coil.load
import com.example.worldexplorer.databinding.ItemDetailCountriesBinding
import com.example.worldexplorer.databinding.ItemQuizOptionBinding

class CountriesDetailAdapter(
    private val context: Context,
    private val borders: List<String>
) : BaseAdapter() {

    override fun getCount(): Int = borders.size
    override fun getItem(position: Int): Any? = null
    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemDetailCountriesBinding

        if (convertView == null) {
            /** Si convertView es nulo, inflamos la vista utilizando ViewBinding */
            binding = ItemDetailCountriesBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
            /** Guardamos la referencia al ViewBinding en la etiqueta de la vista */
        } else {
            /** Si convertView no es nulo, recuperamos el ViewBinding de la etiqueta */
            binding = convertView.tag as ItemDetailCountriesBinding
        }

        binding.ivBorderCountryFlag.load("https://flagcdn.com/w320/${borders[position].lowercase()}.png")
        binding.tvCountryName.text = "Pais $position"
        Log.d("Oscar", "https://flagcdn.com/w320/${borders[position].lowercase()}.png")

        return binding.root
    }
}