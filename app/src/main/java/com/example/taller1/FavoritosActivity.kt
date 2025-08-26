package com.example.taller1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FavoritosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoritosAdapter(MainActivity.favoritos)
    }
}