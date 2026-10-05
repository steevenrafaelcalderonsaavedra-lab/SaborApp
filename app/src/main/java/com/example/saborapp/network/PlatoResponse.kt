package com.example.saborapp.network

data class PlatoResponse(
    val success: Boolean,

    val mensaje: String? = null,

    val platos: List<Plato>? = null
)
