package com.example.saborapp
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.saborapp.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.saborapp.network.LoginResponse
import com.example.saborapp.network.RetrofitClient
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener {

            validarCampos()
        }
    }


    private fun validarCampos() {

        val usuario =
            binding.etUsuario.text.toString().trim()

        val clave =
            binding.etPassword.text.toString().trim()

        binding.tilUsuario.error = null
        binding.tilPassword.error = null

        var correcto = true

        if (usuario.isEmpty()) {

            binding.tilUsuario.error =
                getString(R.string.campo_obligatorio)

            correcto = false
        }

        if (clave.isEmpty()) {

            binding.tilPassword.error =
                getString(R.string.campo_obligatorio)

            correcto = false
        }

        if (!correcto) {
            return
        }

        iniciarSesion(usuario, clave)
    }


    private fun iniciarSesion(
        usuario: String,
        clave: String
    ) {

        RetrofitClient.api
            .login(usuario, clave)
            .enqueue(object : Callback<LoginResponse> {

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if (response.isSuccessful) {

                        val respuesta = response.body()

                        if (respuesta?.success == true) {

                            abrirMenu(
                                respuesta.usuario ?: "",
                                respuesta.rol ?: ""
                            )

                        } else {

                            Toast.makeText(
                                this@LoginActivity,
                                respuesta?.mensaje
                                    ?: "Credenciales incorrectas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {

                        Toast.makeText(
                            this@LoginActivity,
                            "Error del servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


                override fun onFailure(
                    call: Call<LoginResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@LoginActivity,
                        "No se pudo conectar con el servidor",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }


    private fun abrirMenu(
        usuario: String,
        rol: String
    ) {

        val intent =
            Intent(this, MenuActivity::class.java)

        intent.putExtra("NOMBRE", usuario)
        intent.putExtra("ROL", rol)

        startActivity(intent)

        finish()
    }
}