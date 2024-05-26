package com.example.cremallerademontserrat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cremallerademontserrat.R

class Lista(private val origenLi: List<String>, private val context: Context) : RecyclerView.Adapter<Lista.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_de_origen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(origenLi[position])
    }

    override fun getItemCount(): Int = origenLi.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val origenItem: TextView = itemView.findViewById(R.id.textHorari)

        fun bind(origen: String) {
            origenItem.text = origen
        }
    }
}
