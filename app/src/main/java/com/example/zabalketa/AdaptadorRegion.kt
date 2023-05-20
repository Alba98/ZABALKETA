package com.example.zabalketa

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdaptadorRegion (context: Context, val regiones: List<Region>): ArrayAdapter<Region>(context, android.R.layout.simple_spinner_item, regiones) {
    override fun getItemId(position: Int): Long {
        return regiones[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = regiones[position].region
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = regiones[position].region
        return view

    }
}