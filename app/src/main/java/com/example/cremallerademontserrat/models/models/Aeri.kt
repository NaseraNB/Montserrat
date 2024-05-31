package com.example.cremallerademontserrat.models.models

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cremallerademontserrat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

/**
 * Activitat que mostra la informació relativa al servei de l'Aeri de Montserrat.
 */
class Aeri : AppCompatActivity() {

    // Declaració de variables
    private lateinit var aeriMenu: ImageView
    private lateinit var aeriMaig: TextView
    private lateinit var transportAvis: TextView
    private lateinit var aeriFletxaEsquerra: ImageView
    private lateinit var aeriFletxaDreta: ImageView
    private lateinit var aeriCalendari: ImageView
    private lateinit var aeriInfo: Button
    private lateinit var transportBotoInici: TextView
    private lateinit var transport: TextView
    private lateinit var cremalleraComprar: Button
    private lateinit var avis: View

    private var currentMonthIndex: Int = 0
    private val months = arrayOf(
        "Gener", "Febrer", "Març", "Abril", "Maig", "Juny",
        "Juliol", "Agost", "Setembre", "Octubre", "Novembre", "Desembre"
    )
    private val monthImages = arrayOf(
        R.drawable.gener, R.drawable.febrer, R.drawable.mar_, R.drawable.abril,
        R.drawable.maig, R.drawable.juny, R.drawable.juliol, R.drawable.agost,
        R.drawable.setembre, R.drawable.octubre, R.drawable.novembre, R.drawable.desembre
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeri)

        // Inicialització dels elements de la interfície
        aeriMenu = findViewById(R.id.aeriMenu)
        transportAvis = findViewById(R.id.transportAvis)
        aeriMaig = findViewById(R.id.aeriMaig)
        aeriFletxaEsquerra = findViewById(R.id.aeriFletxaEsquerra)
        aeriFletxaDreta = findViewById(R.id.aeriFletxaDreta)
        aeriCalendari = findViewById(R.id.aeriCalendari)
        aeriInfo = findViewById(R.id.button2)
        transportBotoInici = findViewById(R.id.transportBotoInici)
        transport = findViewById(R.id.textView10)
        cremalleraComprar = findViewById(R.id.button3)
        avis = findViewById(R.id.avis)

        // Configuració dels listeners
        obrirMenu()
        avis()
        aeriInfo()
        cremalleraComprar()
        transport()
        transportBotoInici()

        // Obtenir el mes actual
        val calendar = Calendar.getInstance()
        currentMonthIndex = calendar.get(Calendar.MONTH)

        // Actualitzar la visualització del mes
        updateMonthDisplay()

        // Configurar els listeners de les fletxes
        aeriFletxaEsquerra.setOnClickListener {
            currentMonthIndex = if (currentMonthIndex - 1 < 0) 11 else currentMonthIndex - 1
            updateMonthDisplay()
        }

        aeriFletxaDreta.setOnClickListener {
            currentMonthIndex = if (currentMonthIndex + 1 > 11) 0 else currentMonthIndex + 1
            updateMonthDisplay()
        }
    }

    /**
     * Mètode per obrir el menú.
     */
    private fun obrirMenu() {
        aeriMenu.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    /**
     * Mètode per actualitzar la visualització del mes.
     */
    private fun updateMonthDisplay() {
        val monthName = months[currentMonthIndex]
        aeriMaig.text = monthName.capitalize(Locale.ROOT)
        aeriCalendari.setImageResource(monthImages[currentMonthIndex])
    }

    /**
     * Mètode per iniciar l'animació dels avisos.
     */
    private fun iniciarAnimacioAvis() {
        val animacio = AnimationUtils.loadAnimation(this@Aeri, R.anim.avis_anim)
        transportAvis.startAnimation(animacio)
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
        val database =
            FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")
        val reference = database.getReference("AVISOS")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (avisSnapshot in snapshot.children) {
                        val texto = avisSnapshot.child("texto").getValue(String::class.java)
                        val tipo = avisSnapshot.child("tipo").getValue(String::class.java)
                        transportAvis.text = texto
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
     * Mètode per obrir la pàgina web amb la informació de l'Aeri de Montserrat.
     */
    private fun aeriInfo() {
        aeriInfo.setOnClickListener {
            val url = "https://aeridemontserrat.com/horaris-i-tarifes/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    /**
     * Mètode per obrir la pàgina web per comprar els tiquets de cremallera.
     */
    private fun cremalleraComprar() {
        cremalleraComprar.setOnClickListener {
            val url = "https://aeridemontserrat.com/horaris-i-tarifes/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    /**
     * Mètode per navegar a l'activitat de Transport.
     */
    private fun transport() {
        transport.setOnClickListener {
            val intent = Intent(this, Transport::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode per navegar a l'activitat d'Inici.
     */
    private fun transportBotoInici() {
        transportBotoInici.setOnClickListener {
            val intent = Intent(this, Inici::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode que gestiona el comportament al prémer el botó "back".
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Aeri, Transport::class.java)
        startActivity(intent)
        finish()
    }
}


