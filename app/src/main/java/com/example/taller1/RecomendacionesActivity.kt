package com.example.taller1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class RecomendacionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val listaRecomendaciones = findViewById<ListView>(R.id.listaRecomendaciones)
        val textViewTitulo = findViewById<TextView>(R.id.textViewTituloRecomendaciones)

        // Obtener categorías de destinos favoritos
        val categoriasFavoritas = obtenerCategoriasFavoritas()

        if (categoriasFavoritas.isEmpty()) {
            textViewTitulo.text = "No hay destinos favoritos para recomendaciones"
            Toast.makeText(this, "Agrega destinos a favoritos primero", Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener recomendaciones basadas en las categorías
        val recomendaciones = obtenerRecomendaciones(categoriasFavoritas)

        if (recomendaciones.isEmpty()) {
            textViewTitulo.text = "No hay recomendaciones disponibles"
            Toast.makeText(this, "No se encontraron destinos para recomendar", Toast.LENGTH_SHORT).show()
        } else {
            textViewTitulo.text = "Destinos Recomendados (${recomendaciones.size})"

            // Crear lista de nombres de destinos recomendados
            val nombresRecomendaciones = recomendaciones.map { it.nombre }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                nombresRecomendaciones
            )
            listaRecomendaciones.adapter = adapter

            // Manejar clics en los items para ver detalles
            listaRecomendaciones.setOnItemClickListener { parent, view, position, id ->
                val destino = recomendaciones[position]

                val intent = Intent(this, DetalleDestinoActivity::class.java)
                intent.putExtra("destino", destino)
                startActivity(intent)
            }
        }
    }

    private fun obtenerCategoriasFavoritas(): List<String> {
        return MainActivity.favoritos.map { it.categoria }.distinct()
    }

    private fun obtenerRecomendaciones(categorias: List<String>): List<Destino> {
        val todosDestinos = leerTodosDestinosDesdeJson()

        // Filtrar todos los destinos que coincidan con las categorías favoritas
        return todosDestinos
            .filter { destino -> categorias.any { it == destino.categoria } }
            .distinctBy { it.id }
    }

    private fun leerTodosDestinosDesdeJson(): List<Destino> {
        val destinosList = mutableListOf<Destino>()

        try {
            val inputStream = assets.open("destinos.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val destinosArray = jsonObject.getJSONArray("destinos")

            for (i in 0 until destinosArray.length()) {
                val destinoObj = destinosArray.getJSONObject(i)
                val destino = Destino(
                    id = destinoObj.getInt("id"),
                    nombre = destinoObj.getString("nombre"),
                    pais = destinoObj.getString("pais"),
                    categoria = destinoObj.getString("categoria"),
                    plan = destinoObj.getString("plan"),
                    precio = destinoObj.getString("precio")
                )
                destinosList.add(destino)
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error cargando destinos: ${e.message}", Toast.LENGTH_LONG).show()
        }

        return destinosList
    }
}