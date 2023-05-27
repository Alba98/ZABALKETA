package com.example.zabalketa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AdaptadorNiebla(val Nieblas: List<NieblaClase>) : RecyclerView.Adapter<AdaptadorNiebla.ViewHolder>() {
    inner class ViewHolder (v: View): RecyclerView.ViewHolder(v){
        var tvFecha: TextView
        var tvRegion: TextView
        var tvUsername: TextView
        var tvIncidencias : TextView
        var iNiebla : ImageView
        var iLluvia : ImageView
        var iCorteAgua : ImageView
        var id:Int=-1
        init{
            tvFecha=v.findViewById(R.id.tvFecha)
            tvRegion=v.findViewById(R.id.tvRegion)
            tvUsername=v.findViewById(R.id.tvUsername)
            tvIncidencias=v.findViewById(R.id.tvIncidencias)

            iNiebla=v.findViewById(R.id.iNiebla)
            iLluvia=v.findViewById(R.id.iLluvia)
            iCorteAgua=v.findViewById(R.id.iCorteAgua)

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
        holder.tvFecha.text= "${Nieblas[position].fecha}"
        holder.tvRegion.text="${Nieblas[position].region}"
        holder.tvUsername.text="${Nieblas[position].username}"
        holder.tvIncidencias.text ="${Nieblas[position].incidencia}"

        holder.iNiebla.visibility = if (Nieblas[position].hayNiebla) View.VISIBLE else View.INVISIBLE
        holder.iLluvia.visibility = if (Nieblas[position].hayLluvia) View.VISIBLE else View.INVISIBLE
        holder.iNiebla.visibility = if (Nieblas[position].hayCorteAgua) View.VISIBLE else View.INVISIBLE

        // holder.tvDescripcion.text="hayNiebla: ${Nieblas[position].hayNiebla} + " + "hayLluvia: ${Nieblas[position].hayLluvia} +" +  "hayCorteAgua: ${Nieblas[position].hayCorteAgua}"
        holder.id=Nieblas[position].id
    }

    override fun getItemCount(): Int {
        return Nieblas.count()
    }
}