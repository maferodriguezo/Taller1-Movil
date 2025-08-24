package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetalleDestinoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)

        // Obtener el destino pasado desde la actividad anterior
        val destino = intent.getSerializableExtra("destino") as Destino

        // Configurar las vistas con la información del destino
        findViewById<TextView>(R.id.textViewNombre).text = destino.nombre
        findViewById<TextView>(R.id.textViewPais).text = destino.pais
        findViewById<TextView>(R.id.textViewCategoria).text = destino.categoria
        findViewById<TextView>(R.id.textViewDescripcion).text = destino.descripcion
        findViewById<TextView>(R.id.textViewPrecio).text = destino.precio

        // Configurar el botón de favoritos
        val btnFavorito = findViewById<Button>(R.id.btnFavorito)
        btnFavorito.setOnClickListener {
            guardarFavorito(destino)
            btnFavorito.isEnabled = false
            btnFavorito.text = "Añadido a Favoritos"
            Toast.makeText(this, "${destino.nombre} añadido a favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarFavorito(destino: Destino) {
        // Aquí guardar lo de favorito
        // Por ahora solo un Toast
        val sharedPreferences = getSharedPreferences("favoritos", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("ultimo_favorito", destino.nombre)
        editor.apply()
    }
}