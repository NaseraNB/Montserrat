package com.example.cremallerademontserrat.models.models

import Reserva
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cremallerademontserrat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Reserves : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")
    private lateinit var adapter: ReservesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserves)

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val databaseReference: DatabaseReference = database.reference

            databaseReference.child("reservas").orderByChild("usuari_id").equalTo(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        onDataChangeReservas(dataSnapshot)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        mostrarMensaje("Error al acceder a las reservas.")
                    }
                })
        } else {
            mostrarMensaje("Usuario no autenticado.")
        }
    }

    private fun onDataChangeReservas(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.exists()) {
            val novetatsRef = database.reference.child("novetats")
            val listaReservas: MutableList<Reserva> = mutableListOf()

            for (reservaSnapshot in dataSnapshot.children) {
                val reserva = reservaSnapshot.getValue(Reserva::class.java)
                if (reserva != null) {
                    // Obtener el ID de la reserva
                    val reservaId = reserva.id

                    // Obtener los datos de novetats usando el ID de la reserva
                    novetatsRef.child(reservaId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(novetatSnapshot: DataSnapshot) {
                            if (novetatSnapshot.exists()) {
                                val novetatList = novetatSnapshot.getValue(object : GenericTypeIndicator<List<NovetatsBBDD>>() {})
                                if (novetatList != null) {
                                    // Tomar el primer elemento de la lista de novetats
                                    val novetat = novetatList.firstOrNull()

                                    if (novetat != null) {

                                        val titulo = novetatSnapshot.child("nom").getValue(String::class.java)
                                        val fechaInicio = novetatSnapshot.child("data").getValue(String::class.java)
                                        val imagen = novetatSnapshot.child("imatge").getValue(String::class.java)

                                        if (titulo != null && fechaInicio != null && imagen != null) {
                                            novetat.nom = titulo
                                            novetat.data = fechaInicio
                                            novetat.imatge = imagen
                                        }


                                        // Crear un objeto Reserva con los datos de novetats
                                        val reservaUsuario = Reserva(novetat.id, novetat.nom, novetat.data, novetat.imatge)
                                        listaReservas.add(reservaUsuario)

                                        // Notificar al adaptador que los datos han cambiado
                                        adapter.notifyDataSetChanged()
                                    }
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            mostrarMensaje("Error al acceder a los datos de novetats.")
                        }
                    })
                }
            }

            // Mostrar las reservas en el RecyclerView después de obtener todos los datos
            mostrarReservas(listaReservas)
        } else {
            mostrarMensaje("No tienes reservas.")
        }
    }

    private fun mostrarReservas(reservasList: List<Reserva>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = ReservesAdapter(reservasList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Reserves, Inici::class.java)
        startActivity(intent)
        finish()
    }
}
