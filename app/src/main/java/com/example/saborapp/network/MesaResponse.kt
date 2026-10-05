package com.example.saborapp.network

data class MesaResponse(
    val success: Boolean,

    val mensaje: String? = null,

    val mesas: List<Mesa>? = null

)
