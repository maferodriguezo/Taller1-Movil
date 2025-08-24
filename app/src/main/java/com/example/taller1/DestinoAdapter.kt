package com.example.taller1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DestinoAdapter(
    private val context: Context,
    private val destinos: List<Destino>
) : ArrayAdapter<Destino>(context, 0, destinos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.activity_detalle_destino, parent, false)

        val destino = destinos[position]

        view.findViewById<TextView>(R.id.textViewNombre).text = destino.nombre
        view.findViewById<TextView>(R.id.textViewPais).text = destino.pais
        view.findViewById<TextView>(R.id.textViewCategoria).text = destino.categoria
        view.findViewById<TextView>(R.id.textViewPrecio).text = destino.precio
        view.findViewById<TextView>(R.id.textViewDescripcion).text = destino.descripcion

        return view
    }
}