package com.example.taller1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        configurarSpinner()

        configurarBotones()
    }

    private fun configurarSpinner() {
        val spinner = findViewById<Spinner>(R.id.spinnerCategorias)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.categorias_viajes, // Array creado
            android.R.layout.simple_spinner_item // Layout por defecto
        )

        // Especificar el layout para la lista desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicar el adapter al spinner
        spinner.adapter = adapter

        // Opcional: Manejar la selección de items
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Obtener la categoría seleccionada
                val categoriaSeleccionada = parent.getItemAtPosition(position).toString()
                // Puedes usar este valor según necesites
                Toast.makeText(this@MainActivity, "Categoría: $categoriaSeleccionada", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se seleccionó nada
            }
        }
    }

    private fun configurarBotones() {
        // Configurar el botón de Explorar Destinos
        findViewById<Button>(R.id.button1).setOnClickListener {
            val spinner = findViewById<Spinner>(R.id.spinnerCategorias)
            val categoriaSeleccionada = spinner.selectedItem.toString()

            val intent = Intent(this, DestinosActivity::class.java)
            intent.putExtra("categoria", categoriaSeleccionada)
            startActivity(intent)
        }

        // Configurar los otros botones
        findViewById<Button>(R.id.button2).setOnClickListener {
            Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            Toast.makeText(this, "Recomendaciones", Toast.LENGTH_SHORT).show()
        }
    }
}