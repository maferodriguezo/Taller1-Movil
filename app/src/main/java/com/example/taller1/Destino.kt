package com.example.taller1

import java.io.Serializable

data class Destino(
    val id: Int,
    val nombre: String,
    val pais: String,
    val categoria: String,
    val plan: String,
    val precio: String,
): Serializable