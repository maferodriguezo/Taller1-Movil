package com.example.taller1

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import android.content.Intent


class DestinosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinos)

        val categoriaSeleccionada = intent.getStringExtra("categoria")
        val listaDestinos = findViewById<ListView>(R.id.listaDestinos)
        val textViewTitulo = findViewById<TextView>(R.id.textViewTitulo)

        textViewTitulo.text = getString(R.string.destinos_titulo, categoriaSeleccionada)

        // Leer y parsear el JSON manualmente
        val destinos = leerDestinosDesdeJson()

        // Filtrar destinos por categoría
        val destinosFiltrados = if (categoriaSeleccionada == "Todos") {
            destinos
        } else {
            destinos.filter { it.categoria == categoriaSeleccionada }
        }

        // Crear una lista simple de strings para mostrar
        val destinosList = destinosFiltrados.map {it.nombre}

        // Configurar el adaptador simple
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            destinosList
        )
        listaDestinos.adapter = adapter

        // Manejar clics en los items
        listaDestinos.setOnItemClickListener { parent, view, position, id ->
            val destino = destinosFiltrados[position]

            // Abrir actividad de detalle
            val intent = Intent(this, DetalleDestinoActivity::class.java)
            intent.putExtra("destino", destino)
            startActivity(intent)
        }
    }

    private fun leerDestinosDesdeJson(): List<Destino> {
        val destinosList = mutableListOf<Destino>()

        try {
            // Leer desde assets
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
                    precio = destinoObj.getString("precio"),
                )
                destinosList.add(destino)
            }

            // Debug: ver cuántos destinos se cargaron
            Log.d("JSON_DEBUG", "Destinos cargados: ${destinosList.size}")

        } catch (e: Exception) {
            Log.e("JSON_ERROR", "Error loading JSON from assets: ${e.message}")
            e.printStackTrace()
            Toast.makeText(this, "Error cargando destinos: ${e.message}", Toast.LENGTH_LONG).show()
        }

        return destinosList
    }
}