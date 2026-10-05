package com.example.saborapp

import android.widget.Toast
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.saborapp.databinding.ActivityRegistrarPlatoBinding
import com.example.saborapp.network.PlatoResponse
import com.example.saborapp.network.RetrofitClient
class RegistrarPlatoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarPlatoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityRegistrarPlatoBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnGuardar.setOnClickListener {

            validarDatos()
        }
    }


    private fun validarDatos() {

        val nombre =
            binding.etNombre.text.toString().trim()

        val categoria =
            binding.spCategoria.selectedItem.toString()

        val precioTexto =
            binding.etPrecio.text.toString().trim()


        // Limpiar errores anteriores

        binding.tilNombre.error = null
        binding.tilPrecio.error = null


        // VALIDAR NOMBRE

        if (nombre.isEmpty()) {

            binding.tilNombre.error =
                "Ingrese el nombre"

            return
        }


        // VALIDAR CATEGORIA

        if (binding.spCategoria.selectedItemPosition == 0) {

            Toast.makeText(
                this,
                "Seleccione una categoría",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        // VALIDAR PRECIO VACIO

        if (precioTexto.isEmpty()) {

            binding.tilPrecio.error =
                "Ingrese el precio"

            return
        }


        val precio =
            precioTexto.toDoubleOrNull()


        // VALIDAR PRECIO NUMERICO

        if (precio == null || precio <= 0) {

            binding.tilPrecio.error =
                "Ingrese un precio válido"

            return
        }


        // DISPONIBILIDAD

        val disponible = if (
            binding.swDisponible.isChecked
        ) {
            1
        } else {
            0
        }


        registrarPlato(
            nombre,
            categoria,
            precio,
            disponible
        )
    }


    private fun registrarPlato(
        nombre: String,
        categoria: String,
        precio: Double,
        disponible: Int
    ) {

        binding.btnGuardar.isEnabled = false


        RetrofitClient.api
            .registrarPlato(
                nombre,
                categoria,
                precio,
                disponible
            )
            .enqueue(
                object : Callback<PlatoResponse> {

                    override fun onResponse(
                        call: Call<PlatoResponse>,
                        response: Response<PlatoResponse>
                    ) {

                        binding.btnGuardar.isEnabled = true

                        if (response.isSuccessful) {

                            val respuesta =
                                response.body()


                            if (respuesta?.success == true) {

                                Toast.makeText(
                                    this@RegistrarPlatoActivity,
                                    respuesta.mensaje,
                                    Toast.LENGTH_SHORT
                                ).show()


                                // Cerrar formulario
                                // y regresar a Platos
                                finish()

                            } else {

                                Toast.makeText(
                                    this@RegistrarPlatoActivity,
                                    respuesta?.mensaje
                                        ?: "Error al registrar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {

                            Toast.makeText(
                                this@RegistrarPlatoActivity,
                                "Error del servidor",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    override fun onFailure(
                        call: Call<PlatoResponse>,
                        t: Throwable
                    ) {

                        binding.btnGuardar.isEnabled = true

                        Toast.makeText(
                            this@RegistrarPlatoActivity,
                            "No se pudo conectar al servidor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }
}