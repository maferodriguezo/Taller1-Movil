package com.example.taller1

import android.view.LayoutInflater
import android.view.ViewGroup

class FavoritosAdapter(private val listaFavoritos: List<Destino>) :
        RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder>() {

inner  : RecyclerView.ViewHolder(itemView) {
    val nombreDestino: TextView = itemView.findViewById(R.id.textoDestino)
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritoViewHolder {
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorito, parent, false)
    return FavoritoViewHolder(view)
}

override fun onBindViewHolder(holder: FavoritoViewHolder, position: Int) {
    val destino = listaFavoritos[position]
    holder.nombreDestino.text = destino.nombre
}

override fun getItemCount(): Int = listaFavoritos.size
}
