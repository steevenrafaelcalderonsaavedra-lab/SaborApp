package com.example.saborapp
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import com.example.saborapp.databinding.ActivityMenuBinding
class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir información del Login
        val rol = intent.getStringExtra("ROL") ?: "MOZO"
        val nombre = intent.getStringExtra("NOMBRE") ?: "Usuario"

        // Mostrar información
        binding.tvNombre.text = nombre
        binding.tvRol.text = getString(R.string.rol_usuario, rol)

        // REPORTES solamente para ADMIN
        if (rol == "ADMIN") {

            binding.btnReportes.visibility = View.VISIBLE

        } else {

            binding.btnReportes.visibility = View.GONE
        }


        // PLATOS
        binding.btnPlatos.setOnClickListener {

            val intent = Intent(
                this,
                PlatosActivity::class.java
            )

            startActivity(intent)
        }


        // MESAS
        binding.btnMesas.setOnClickListener {

            val intent = Intent(
                this,
                MesasActivity::class.java
            )

            startActivity(intent)
        }


        // PEDIDOS
        binding.btnPedidos.setOnClickListener {

            val intent = Intent(
                this,
                PedidoActivity::class.java
            )

            startActivity(intent)
        }


        // REPORTES
        binding.btnReportes.setOnClickListener {

            val intent = Intent(
                this,
                ReportesActivity::class.java
            )

            startActivity(intent)
        }


        // SALIR
        binding.btnSalir.setOnClickListener {

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}