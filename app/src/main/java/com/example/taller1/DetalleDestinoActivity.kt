package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.taller1.model.WeatherResponse
import com.example.taller1.network.WeatherService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DetalleDestinoActivity : AppCompatActivity() {

    private lateinit var textViewClima: TextView
    private val apiKey = "3b8281331d898c0bf24828e408bb93e6" // tu API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)

        // Obtener el destino pasado desde la actividad anterior
        val destino = intent.getSerializableExtra("destino") as Destino

        // Configurar las vistas con la información del destino
        findViewById<TextView>(R.id.textViewNombre).text = destino.nombre
        findViewById<TextView>(R.id.textViewPais).text = destino.pais
        findViewById<TextView>(R.id.textViewCategoria).text = destino.categoria
        findViewById<TextView>(R.id.textViewPrecio).text = destino.precio

        // Clima
        textViewClima = findViewById(R.id.textViewClima)
        obtenerClima(destino.nombre, destino.pais)

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
        if (!MainActivity.favoritos.any { it.id == destino.id }) {
            MainActivity.favoritos.add(destino)
            Toast.makeText(this, "${destino.nombre} añadido a favoritos", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "${destino.nombre} ya está en favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    // 🔹 Obtener clima (intenta con ciudad y si falla usa país)
    private fun obtenerClima(ciudad: String, pais: String? = null) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.getWeather(ciudad, apiKey = apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val clima = response.body()
                    val temp = clima?.main?.temp
                    val descripcion = clima?.weather?.get(0)?.description
                    textViewClima.text = "Clima: $descripcion, ${temp}°C"
                } else {
                    // fallback: probar con el país si no funcionó la ciudad
                    pais?.let { obtenerClima(it, null) }
                        ?: run { textViewClima.text = "Clima no disponible" }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // fallback: probar con el país si no funcionó la ciudad
                pais?.let { obtenerClima(it, null) }
                    ?: run { textViewClima.text = "Error al obtener clima" }
            }
        })
    }
}
