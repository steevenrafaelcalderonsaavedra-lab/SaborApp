package com.example.saborapp
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.saborapp.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener {

            validarLogin()

        }
    }

    private fun validarLogin() {

        val usuario = binding.etUsuario.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        // Limpiar errores anteriores
        binding.tilUsuario.error = null
        binding.tilPassword.error = null

        var correcto = true

        // CA1: validar campos vacíos
        if (usuario.isEmpty()) {
            binding.tilUsuario.error =
                getString(R.string.campo_obligatorio)
            correcto = false
        }

        if (password.isEmpty()) {
            binding.tilPassword.error =
                getString(R.string.campo_obligatorio)
            correcto = false
        }

        if (!correcto) {
            return
        }

        // ADMINISTRADOR
        if (usuario == "admin" && password == "1234") {

            abrirMenu("ADMIN", "Administrador")

        }
        // MOZO
        else if (usuario == "mozo" && password == "1234") {

            abrirMenu("MOZO", "Mozo")

        }
        // CREDENCIALES INCORRECTAS
        else {

            Toast.makeText(
                this,
                "Credenciales incorrectas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun abrirMenu(rol: String, nombre: String) {

        val intent = Intent(this, MenuActivity::class.java)

        intent.putExtra("ROL", rol)
        intent.putExtra("NOMBRE", nombre)

        startActivity(intent)

        // Evita regresar al login con Atrás
        finish()
    }
}