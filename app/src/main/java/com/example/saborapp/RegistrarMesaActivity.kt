package com.example.saborapp

import android.widget.Toast
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.saborapp.databinding.ActivityRegistrarMesaBinding
import com.example.saborapp.network.MesaResponse
import com.example.saborapp.network.RetrofitClient
class RegistrarMesaActivity : AppCompatActivity() {
    private lateinit var binding:
            ActivityRegistrarMesaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityRegistrarMesaBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)


        binding.btnGuardarMesa.setOnClickListener {

            validarDatos()
        }
    }


    private fun validarDatos() {

        val numeroTexto =
            binding.etNumero.text
                .toString()
                .trim()

        val capacidadTexto =
            binding.etCapacidad.text
                .toString()
                .trim()


        // LIMPIAR ERRORES

        binding.tilNumero.error = null
        binding.tilCapacidad.error = null


        // NUMERO VACIO

        if (numeroTexto.isEmpty()) {

            binding.tilNumero.error =
                getString(
                    R.string.ingrese_numero_mesa
                )

            return
        }


        // CAPACIDAD VACIA

        if (capacidadTexto.isEmpty()) {

            binding.tilCapacidad.error =
                getString(
                    R.string.ingrese_capacidad
                )

            return
        }


        val numero =
            numeroTexto.toIntOrNull()

        val capacidad =
            capacidadTexto.toIntOrNull()


        // VALIDAR NUMERO

        if (numero == null || numero <= 0) {

            binding.tilNumero.error =
                "Número inválido"

            return
        }


        // VALIDAR CAPACIDAD

        if (
            capacidad == null ||
            capacidad < 1 ||
            capacidad > 12
        ) {

            binding.tilCapacidad.error =
                getString(
                    R.string.capacidad_invalida
                )

            return
        }


        registrarMesa(
            numero,
            capacidad
        )
    }


    private fun registrarMesa(
        numero: Int,
        capacidad: Int
    ) {

        binding.btnGuardarMesa.isEnabled =
            false


        RetrofitClient.api
            .registrarMesa(
                numero,
                capacidad
            )
            .enqueue(
                object : Callback<MesaResponse> {


                    override fun onResponse(
                        call: Call<MesaResponse>,
                        response: Response<MesaResponse>
                    ) {

                        binding.btnGuardarMesa
                            .isEnabled = true


                        if (response.isSuccessful) {

                            val respuesta =
                                response.body()


                            if (
                                respuesta?.success == true
                            ) {

                                Toast.makeText(
                                    this@RegistrarMesaActivity,
                                    respuesta.mensaje,
                                    Toast.LENGTH_SHORT
                                ).show()


                                finish()

                            } else {

                                Toast.makeText(
                                    this@RegistrarMesaActivity,
                                    respuesta?.mensaje
                                        ?: "Error al registrar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {

                            Toast.makeText(
                                this@RegistrarMesaActivity,
                                "Error del servidor",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    override fun onFailure(
                        call: Call<MesaResponse>,
                        t: Throwable
                    ) {

                        binding.btnGuardarMesa
                            .isEnabled = true


                        Toast.makeText(
                            this@RegistrarMesaActivity,
                            "No se pudo conectar al servidor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }
}