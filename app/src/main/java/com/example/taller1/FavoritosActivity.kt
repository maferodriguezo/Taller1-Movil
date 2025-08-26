package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class FavoritosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val listaFavoritos = findViewById<ListView>(R.id.listaFavoritos)
        val textViewTitulo = findViewById<TextView>(R.id.textViewTituloFavoritos)

        // Mostrar lista de favoritos
        if (MainActivity.favoritos.isEmpty()) {
            textViewTitulo.text = "No hay destinos favoritos"
            Toast.makeText(this, "No hay destinos en favoritos", Toast.LENGTH_SHORT).show()
        } else {
            textViewTitulo.text = "Destinos Favoritos (${MainActivity.favoritos.size})"

            // Crear lista de nombres de destinos favoritos
            val nombresFavoritos = MainActivity.favoritos.map { it.nombre }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                nombresFavoritos
            )
            listaFavoritos.adapter = adapter

            // Manejar clics en los items para ver detalles
            listaFavoritos.setOnItemClickListener { parent, view, position, id ->
                val destino = MainActivity.favoritos[position]

                val intent = Intent(this, DetalleDestinoActivity::class.java)
                intent.putExtra("destino", destino)
                startActivity(intent)
            }
        }
    }
}