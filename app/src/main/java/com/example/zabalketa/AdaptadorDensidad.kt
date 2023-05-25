package com.example.zabalketa

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdaptadorDensidad (context: Context, val densidades: List<Densidad>): ArrayAdapter<Densidad>(context, android.R.layout.simple_spinner_item, densidades) {
    override fun getItemId(position: Int): Long {
        return densidades[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = densidades[position].densidad
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = densidades[position].densidad
        return view

    }
}