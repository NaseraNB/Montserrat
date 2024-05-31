package com.example.cremallerademontserrat.models.models

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

/**
 * Classe que gestiona l'activitat de les rutes a Montserrat.
 */
class Rutes : AppCompatActivity() {

    private lateinit var rutesImatge: ImageView
    private lateinit var avis: View
    private lateinit var rutesAvis: TextView
    private lateinit var mapa: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutes)

        rutesImatge = findViewById(R.id.rutesImatge)
        avis = findViewById(R.id.avis)
        rutesAvis = findViewById(R.id.rutesAvis)

        rutesImatge.setOnClickListener {
            obrirMenu()
        }

        avis()
    }

    /**
     * Mètode que obre el menú.
     */
    private fun obrirMenu() {
        rutesImatge.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    /**
     * Mètode per iniciar l'animació d'avís.
     */
    private fun iniciarAnimacioAvis() {
        val animacio = AnimationUtils.loadAnimation(this@Rutes, R.anim.avis_anim)
        rutesAvis.startAnimation(animacio)
        animacio.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                Handler().postDelayed({
                    iniciarAnimacioAvis()
                }, 6000)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    /**
     * Mètode per obtenir l'últim avís de la base de dades.
     */
    private fun avis() {
        val database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")
        val reference = database.getReference("AVISOS")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (avisSnapshot in snapshot.children) {
                        val texto = avisSnapshot.child("texto").getValue(String::class.java)
                        val tipo = avisSnapshot.child("tipo").getValue(String::class.java)
                        rutesAvis.text = texto
                        iniciarAnimacioAvis()
                        val color = when (tipo) {
                            "Info" -> Color.BLUE
                            "Emergency" -> Color.RED
                            "Alert" -> Color.YELLOW
                            else -> Color.WHITE
                        }
                        avis.setBackgroundColor(color)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("IniciActivity", "Error al obtenir les dades", error.toException())
            }
        })
    }

    /**
     * Mètode que retrocedeix a una altra activitat,
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Rutes, Inici::class.java)
        startActivity(intent)
        finish()
    }
}
