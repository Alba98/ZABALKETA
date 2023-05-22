package com.example.zabalketa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AdaptadorNiebla(val Nieblas: List<Niebla>) : RecyclerView.Adapter<AdaptadorNiebla.ViewHolder>() {
    inner class ViewHolder (v: View): RecyclerView.ViewHolder(v){
        var tvFecha: TextView
        var tvRegion: TextView
        var tvDescripcion: TextView
        var id:Int=-1
        init{
            tvFecha=v.findViewById(R.id.tvFecha)
            tvRegion=v.findViewById(R.id.tvRegion)
            tvDescripcion=v.findViewById(R.id.tvDescripcion)
            v.setOnClickListener{
                val bundle= bundleOf("id" to id)
                v.findNavController().navigate(R.id.action_SecondFragment_to_datosFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.layout_card, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFecha.text="FECHA: ${Nieblas[position].fecha}"
        holder.tvRegion.text="REGION: ${Nieblas[position].idRegion}"
        holder.tvDescripcion.text="${Nieblas[position].idDensidad}"
        holder.id=Nieblas[position].id
    }

    override fun getItemCount(): Int {
        return Nieblas.count()
    }
}