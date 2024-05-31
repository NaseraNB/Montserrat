package com.example.cremallerademontserrat.models.models

import Reserva
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cremallerademontserrat.R
import com.google.android.material.imageview.ShapeableImageView

class ReservesAdapter(private val reservesList: List<Reserva>) : RecyclerView.Adapter<ReservesAdapter.ReservaViewHolder>() {

    inner class ReservaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.reservaTitol)
        val fechaTextView: TextView = itemView.findViewById(R.id.dataReserva)
        val imatge: ShapeableImageView = itemView.findViewById(R.id.imatgeReserva)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.llista_reserva, parent, false)
        return ReservaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val currentReserva = reservesList[position]
        holder.tituloTextView.text = currentReserva.nom
        holder.fechaTextView.text = currentReserva.data

        // Cargar la imagen usando Glide
        Glide.with(holder.itemView.context)
            .load(currentReserva.imatge) // Suponiendo que currentReserva.imatge es la URL de la imagen
            .into(holder.imatge) // holder.imatge es un ShapeableImageView
    }

    override fun getItemCount(): Int {
        return reservesList.size
    }
}
