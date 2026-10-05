package com.example.saborapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.saborapp.databinding.ActivityMesasBinding
import com.example.saborapp.network.MesaResponse
import com.example.saborapp.network.RetrofitClient
class MesasActivity : AppCompatActivity() {
    private lateinit var binding:
            ActivityMesasBinding

    private lateinit var adapter:
            MesaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMesasBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)


        // RECYCLERVIEW

        adapter =
            MesaAdapter(emptyList())


        binding.rvMesas.layoutManager =
            LinearLayoutManager(this)


        binding.rvMesas.adapter =
            adapter


        // NUEVA MESA

        binding.btnNuevaMesa.setOnClickListener {

            val intent =
                Intent(
                    this,
                    RegistrarMesaActivity::class.java
                )

            startActivity(intent)
        }
    }


    override fun onResume() {

        super.onResume()

        cargarMesas()
    }


    private fun cargarMesas() {

        RetrofitClient.api
            .listarMesas()
            .enqueue(
                object : Callback<MesaResponse> {


                    override fun onResponse(
                        call: Call<MesaResponse>,
                        response: Response<MesaResponse>
                    ) {

                        if (
                            response.isSuccessful
                        ) {

                            val respuesta =
                                response.body()


                            if (
                                respuesta?.success == true
                            ) {

                                val mesas =
                                    respuesta.mesas
                                        ?: emptyList()


                                adapter.actualizarLista(
                                    mesas
                                )
                            }

                        } else {

                            Toast.makeText(
                                this@MesasActivity,
                                "Error al cargar mesas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    override fun onFailure(
                        call: Call<MesaResponse>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@MesasActivity,
                            "No se pudo conectar con el servidor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }
}