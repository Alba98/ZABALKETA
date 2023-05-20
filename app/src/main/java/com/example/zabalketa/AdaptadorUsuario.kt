package com.example.zabalketa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AdaptadorUsuario(val usuarios: List<UsuarioClase>) : RecyclerView.Adapter<AdaptadorUsuario.ViewHolder>() {
    inner class ViewHolder (v: View): RecyclerView.ViewHolder(v){
        var tvUsername: TextView
        var tvRegion: TextView
        var id:Int=-1
        init{
            tvUsername=v.findViewById(R.id.tvUsername)
            tvRegion=v.findViewById(R.id.tvRegion)
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
        holder.tvUsername.text="Título: ${usuarios[position].username}"
        holder.tvRegion.text="Género: ${usuarios[position].region}"
        holder.id=usuarios[position].id
    }

    override fun getItemCount(): Int {
        return usuarios.count()
    }
}