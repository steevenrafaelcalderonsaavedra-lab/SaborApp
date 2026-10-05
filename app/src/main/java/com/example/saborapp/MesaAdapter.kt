package com.example.saborapp
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saborapp.databinding.ItemMesaBinding
import com.example.saborapp.network.Mesa
class MesaAdapter(
    private var lista: List<Mesa>
) : RecyclerView.Adapter<MesaAdapter.MesaViewHolder>() {


    class MesaViewHolder(
        val binding: ItemMesaBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    )


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MesaViewHolder {

        val binding =
            ItemMesaBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )

        return MesaViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: MesaViewHolder,
        position: Int
    ) {

        val mesa =
            lista[position]


        holder.binding.tvNumeroMesa.text =
            "Mesa ${mesa.numero}"


        holder.binding.tvCapacidad.text =
            "Capacidad: ${mesa.capacidad} personas"


        holder.binding.tvEstado.text =
            mesa.estado
    }


    override fun getItemCount(): Int {

        return lista.size
    }


    fun actualizarLista(
        nuevaLista: List<Mesa>
    ) {

        lista = nuevaLista

        notifyDataSetChanged()
    }
}