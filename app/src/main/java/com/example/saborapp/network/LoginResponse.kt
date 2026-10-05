package com.example.saborapp.network

data class LoginResponse(
    val success: Boolean,

    val id: Int?,

    val usuario: String?,

    val rol: String?,

    val mensaje: String?
)
