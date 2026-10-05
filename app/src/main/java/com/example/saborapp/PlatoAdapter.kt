package com.example.saborapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saborapp.databinding.ItemPlatoBinding
import com.example.saborapp.network.Plato
class PlatoAdapter(
    private var lista: List<Plato>
) : RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>() {


    class PlatoViewHolder(
        val binding: ItemPlatoBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlatoViewHolder {

        val binding =
            ItemPlatoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return PlatoViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: PlatoViewHolder,
        position: Int
    ) {

        val plato = lista[position]

        holder.binding.tvNombre.text =
            plato.nombre

        holder.binding.tvCategoria.text =
            plato.categoria

        holder.binding.tvPrecio.text =
            "S/ %.2f".format(plato.precio)

        holder.binding.tvDisponible.text =
            if (plato.disponible == 1) {
                "DISPONIBLE"
            } else {
                "NO DISPONIBLE"
            }
    }


    override fun getItemCount(): Int {

        return lista.size
    }


    fun actualizarLista(
        nuevaLista: List<Plato>
    ) {

        lista = nuevaLista

        notifyDataSetChanged()
    }
}