package com.example.worldexplorer.ui.detailquiz.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.worldexplorer.R


class QuizDetailAdapter(
    private val context: Context,
    private val dataList: List<String>,
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var courseTV: TextView

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView1 = convertView

        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView1 == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView1 = layoutInflater!!.inflate(R.layout.item_quiz_gridview, null)
        }

        // on below line we are initializing our course image view
        // and course text view with their ids.
        courseTV = convertView1!!.findViewById(R.id.tvGridOption)

        Log.d("grid", "data:, view: ${courseTV}")
        // on below line we are setting text in our course text view.
        courseTV.setText(dataList.get(position))

        val parentHeight = parent?.height ?: 0
        courseTV.height = parentHeight/(dataList.size/2) - 32

        // at last we are returning our convert view.
        return convertView1
    }

}