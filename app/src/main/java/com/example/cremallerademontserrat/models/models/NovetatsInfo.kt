package com.example.cremallerademontserrat.models.models

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cremallerademontserrat.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Novetats info
 *
 * @constructor Create empty Novetats info
 */
class NovetatsInfo : AppCompatActivity() {

    private lateinit var transportMenu: ImageView
    private lateinit var novetatsEscolanía: TextView
    private lateinit var novetatsImatge: ShapeableImageView
    private lateinit var escolaniaData: TextView
    private lateinit var novetatsText: TextView
    private lateinit var novetatReservaBoto: Button
    private lateinit var novetatsAvis: TextView
    private lateinit var avis: View
    private lateinit var textView10: TextView
    private lateinit var transportBotoInici: TextView
    private val database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novetats_info)


        transportMenu = findViewById(R.id.transportMenu)
        novetatsEscolanía = findViewById(R.id.novetatsEscolanía)
        novetatsImatge = findViewById(R.id.novetatsImatge)
        escolaniaData = findViewById(R.id.escolaniaData)
        novetatsText = findViewById(R.id.novetatsText)
        novetatReservaBoto = findViewById(R.id.novetatReservaBoto)

        novetatsAvis = findViewById(R.id.novetatsAvis)
        avis = findViewById(R.id.avis)
        textView10 = findViewById(R.id.textView10)
        transportBotoInici = findViewById(R.id.transportBotoInici)

        // Receive the intent and extract the data
        val intent = intent
        val titulo = intent.getStringExtra("titulo")
        val fechaInicio = intent.getStringExtra("fechaInicio")
        val descripcion = intent.getStringExtra("descripcion")
        val imageUrl = intent.getStringExtra("imagen")
        val novedadId = intent.getStringExtra("novedadId")

        // Concatenate fechaInicio y fechaFinal
        val fechaConcatenada = "$fechaInicio"
        // Setear los datos en las vistas correspondientes
        novetatsEscolanía.text = titulo
        escolaniaData.text = fechaConcatenada
        novetatsText.text = descripcion

        // Cargar la imagen utilizando Glide
        Glide.with(this)
            .load(imageUrl)
            .into(novetatsImatge)

        transportMenu.setOnClickListener {
            obrirMenu()
        }

        novetatReservaBoto.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                mostrarDialogoReserva()
            } else {
                verificarEdat(user.uid, novedadId)
            }
        }

        botoInici()
        botoTransport()
        avis()

        // Llamar al método para obtener la última novedad si no se recibió ninguna de la actividad Novetats
        if (titulo == null && fechaInicio == null && descripcion == null && imageUrl == null) {
            obtenerUltimaNovedad()
        }
    }


    private fun verificarEdat(userId: String, novedadId: String?) {
        val reference = database.getReference("USUARIS").child(userId)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val edat = snapshot.child("edat").getValue(Int::class.java)
                if (edat != null && edat >= 18) {
                    aFetUnaReserva(userId, novedadId)
                } else {
                    Toast.makeText(this@NovetatsInfo, "Per poder fer una reserva has de ser major d'edat", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("NovetatsInfo", "ERROR", error.toException())
            }
        })
    }

    private fun aFetUnaReserva(userId: String, novedadId: String?) {

        if (novedadId != null) {
            val reference = database.getReference("novetats").child(novedadId)

            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.hasChild("reserva_id")) {
                        val reservaId = snapshot.child("reserva_id").getValue(String::class.java)
                        if (reservaId == null) {
                            ferReserva(userId, novedadId)
                        } else {
                            val usuarioIdReserva = snapshot.child("usuari_id").getValue(String::class.java)
                            if (usuarioIdReserva == userId) {

                                Toast.makeText(this@NovetatsInfo, "Ja has fet una reserva anteriorment per a aquesta novetat", Toast.LENGTH_SHORT).show()
                            } else {

                                ferReserva(userId, novedadId)
                            }
                        }
                    } else {
                        ferReserva(userId, novedadId)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("NovetatsInfo", "ERROR", error.toException())
                }
            })
        } else {
            // Si novedadId es nulo, mostrar un mensaje de error
            Toast.makeText(this, "Error al obtener la información de la novedad", Toast.LENGTH_SHORT).show()
        }
    }



    private fun ferReserva(userId: String, novedadId: String?) {
        val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        // Crear un nuevo nodo de reserva
        val reservaReference = database.getReference("reservas").push()

        // Crear un objeto con los detalles de la reserva
        val reserva = mapOf(
            "novedad_id" to novedadId,
            "usuari_id" to userId,
            "data" to fecha,
            "hora" to hora
        )

        // Guardar la reserva en la base de datos
        reservaReference.setValue(reserva)
            .addOnSuccessListener {
                // Obtener el ID de la reserva creada
                val reservaId = reservaReference.key

                // Actualizar el nodo de la novedad con el ID de la reserva
                if (novedadId != null) {
                    val novedadReference = database.getReference("novetats").child(novedadId)
                    novedadReference.child("reserva_id").setValue(reservaId)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Reserva realitzada amb èxit", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al actualizar la reserva en la novedad: ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.d("Error", "Error al actualizar la reserva en la novedad")
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al realizar la reserva: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("Error", "Error al realizar la reserva")
            }
    }

    private fun obtenerUltimaNovedad() {
        val reference = database.getReference("novetats")

        reference.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.childrenCount > 0) {
                    val lastNovedadSnapshot = snapshot.children.lastOrNull() // Obtener la última instantánea de datos

                    if (lastNovedadSnapshot != null) {
                        val titulo = lastNovedadSnapshot.child("nom").getValue(String::class.java)
                        val descripcion = lastNovedadSnapshot.child("descripcio").getValue(String::class.java)
                        val fechaInicio = lastNovedadSnapshot.child("data").getValue(String::class.java)
                        val imagen = lastNovedadSnapshot.child("imatge").getValue(String::class.java)
                        val novedadId = lastNovedadSnapshot.child("id").getValue(String::class.java)

                        if (titulo != null && fechaInicio != null && descripcion != null && imagen != null && novedadId != null) {
                            novetatsEscolanía.text = titulo
                            escolaniaData.text = fechaInicio
                            novetatsText.text = descripcion

                            Glide.with(this@NovetatsInfo)
                                .load(imagen)
                                .into(novetatsImatge)

                            novetatReservaBoto.setOnClickListener {
                                val user = FirebaseAuth.getInstance().currentUser
                                if (user == null) {
                                    mostrarDialogoReserva()
                                } else {
                                    verificarEdat(user.uid, novedadId)
                                }
                            }
                        } else {
                            Toast.makeText(this@NovetatsInfo, "Error en obtenir la informació de la novetat", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@NovetatsInfo, "No s'han trobat novetats", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("NovetatsInfo", "Error fetching data", error.toException())
                Toast.makeText(this@NovetatsInfo, "Error al obtener la información de la novedad", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogoReserva() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialeg_reserva)

        val btnIniciarSesion = dialog.findViewById<Button>(R.id.button)
        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, Inici_De_Sessio::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }

    // Mètode que executa l'animació de l'avís
    private fun iniciarAnimacioAvis() {
        // Emmagatzemar l'animació en una variable
        val animacio = AnimationUtils.loadAnimation(this@NovetatsInfo, R.anim.avis_anim)

        // Aplicar l'animació a l'avís
        novetatsAvis.startAnimation(animacio)

        // Reinicieu l'animació quan acabi
        animacio.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Espereu un temps abans de tornar a iniciar l'animació
                Handler().postDelayed({
                    iniciarAnimacioAvis()
                }, 6000)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    // Mètode que agafa l'últim avís de la base de dades
    private fun avis() {
        // Taula
        val reference = database.getReference("AVISOS")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Verificar si hi ha dades
                if (snapshot.exists()) {
                    for (avisSnapshot in snapshot.children) {

                        // Obtenir els dades i emmagatzemar-los en variables
                        val texto = avisSnapshot.child("texto").getValue(String::class.java)
                        val tipo = avisSnapshot.child("tipo").getValue(String::class.java)

                        // Mostra l'avís
                        novetatsAvis.text = texto

                        // Inicieu l'animació
                        iniciarAnimacioAvis()

                        // Assignar color segons el tipus d'avís
                        val color = when (tipo) {
                            "Info" -> Color.BLUE
                            "Emergency" -> Color.RED
                            "Alert" -> Color.YELLOW
                            else -> Color.WHITE
                        }

                        // Establir el color de fons de l'Avís
                        avis.setBackgroundColor(color)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("IniciActivity", "Error en agafar les dades", error.toException())
            }
        })
    }


    // Mètode de canviar a una altra activitat
    private fun botoInici() {
        transportBotoInici.setOnClickListener {
            val intent = Intent(this, Inici::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun botoTransport() {
        textView10.setOnClickListener {
            val intent = Intent(this, Transport::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun obrirMenu() {
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    // Mètode que indica a l'activitat que podeu tornar
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@NovetatsInfo, Novetats::class.java)
        startActivity(intent)
        finish()
    }
}
