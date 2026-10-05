package com.example.saborapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.saborapp.databinding.ActivityPlatosBinding
import com.example.saborapp.network.PlatoResponse
import com.example.saborapp.network.RetrofitClient
class PlatosActivity : AppCompatActivity() {
    private lateinit var binding:
            ActivityPlatosBinding

    private lateinit var adapter:
            PlatoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityPlatosBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)


        // CONFIGURAR RECYCLERVIEW

        adapter = PlatoAdapter(emptyList())

        binding.rvPlatos.layoutManager =
            LinearLayoutManager(this)

        binding.rvPlatos.adapter =
            adapter


        // BOTON NUEVO PLATO

        binding.btnNuevoPlato.setOnClickListener {

            val intent =
                Intent(
                    this,
                    RegistrarPlatoActivity::class.java
                )

            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()

        // Cada vez que volvemos a esta pantalla
        // actualizamos los platos

        cargarPlatos()
    }


    private fun cargarPlatos() {

        RetrofitClient.api
            .listarPlatos()
            .enqueue(
                object : Callback<PlatoResponse> {


                    override fun onResponse(
                        call: Call<PlatoResponse>,
                        response: Response<PlatoResponse>
                    ) {

                        if (response.isSuccessful) {

                            val respuesta =
                                response.body()


                            if (respuesta?.success == true) {

                                val platos =
                                    respuesta.platos
                                        ?: emptyList()

                                adapter.actualizarLista(
                                    platos
                                )
                            }

                        } else {

                            Toast.makeText(
                                this@PlatosActivity,
                                "Error al cargar platos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    override fun onFailure(
                        call: Call<PlatoResponse>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@PlatosActivity,
                            "No se pudo conectar con el servidor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }
}