package com.example.saborapp.network
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    fun login(

        @Field("usuario")
        usuario: String,

        @Field("clave")
        clave: String

    ): Call<LoginResponse>
    // REGISTRAR PLATO

    @FormUrlEncoded
    @POST("platos/registrar.php")
    fun registrarPlato(

        @Field("nombre")
        nombre: String,

        @Field("categoria")
        categoria: String,

        @Field("precio")
        precio: Double,

        @Field("disponible")
        disponible: Int

    ): Call<PlatoResponse>
// ========================
// MESAS
// ========================

    @FormUrlEncoded
    @POST("mesas/registrar.php")
    fun registrarMesa(

        @Field("numero")
        numero: Int,

        @Field("capacidad")
        capacidad: Int

    ): Call<MesaResponse>


    @GET("mesas/listar.php")
    fun listarMesas(): Call<MesaResponse>

    // LISTAR PLATOS

    @GET("platos/listar.php")
    fun listarPlatos(): Call<PlatoResponse>

}
