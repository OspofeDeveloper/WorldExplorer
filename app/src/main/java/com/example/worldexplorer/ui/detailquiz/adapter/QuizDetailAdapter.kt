package com.example.worldexplorer.ui.detailquiz.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.worldexplorer.databinding.ItemQuizOptionBinding


class QuizDetailAdapter(
    private val context: Context,
    private val dataList: List<String>,
) :
    BaseAdapter() {

    override fun getCount(): Int = dataList.size
    override fun getItem(position: Int): Any? = null
    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemQuizOptionBinding

        if (convertView == null) {
            /** Si convertView es nulo, inflamos la vista utilizando ViewBinding*/
            binding = ItemQuizOptionBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
            /**Guardamos la referencia al ViewBinding en la etiqueta de la vista*/
        } else {
            /**Si convertView no es nulo, recuperamos el ViewBinding de la etiqueta*/
            binding = convertView.tag as ItemQuizOptionBinding
        }

        binding.tvGridOption.text = dataList[position]

        val parentHeight = parent?.height ?: 0
        val itemHeight = parentHeight / (dataList.size / 2) - 32
        binding.tvGridOption.layoutParams.height = itemHeight

        return binding.root
    }

}