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
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Transport
 *
 * @constructor Create empty Transport
 */
class Transport : AppCompatActivity() {

    // Declaració de variables
    private lateinit var transportMenu: ImageView
    private lateinit var imageCremallera: ShapeableImageView
    private lateinit var transportBotoInici: TextView
    private lateinit var aeriImatges: ShapeableImageView
    private lateinit var santJoan: ShapeableImageView
    private lateinit var santaCova: ShapeableImageView
    private lateinit var transportAvis: TextView
    private lateinit var avis: View

    /**
     * Mètode que s'executa quan es crea l'activitat.
     *
     * @param savedInstanceState Estat anterior de l'activitat.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)

        // Inicialització de variables amb els elements del layout
        transportMenu = findViewById(R.id.transportMenu)
        imageCremallera = findViewById(R.id.imageCremallera)
        transportBotoInici = findViewById(R.id.transportBotoInici)
        aeriImatges = findViewById(R.id.aeriImatges)
        santJoan = findViewById(R.id.imageView4)
        santaCova = findViewById(R.id.imageView3)
        transportAvis = findViewById(R.id.transportAvis)
        avis = findViewById(R.id.avis)

        // Crida a mètodes per configurar listeners
        obrirMenu()
        obrirElCremallera()
        obrirInici()
        obrirElAeri()
        obrirElFunicularSantJoan()
        obrirElFunicularSantaCova()
        avis()
    }

    /**
     * Mètode que obre el menú quan es fa clic a la icona del menú.
     */
    private fun obrirMenu() {
        transportMenu.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    /**
     * Mètode que obre l'activitat del Cremallera quan es fa clic a la imatge corresponent.
     */
    private fun obrirElCremallera() {
        imageCremallera.setOnClickListener {
            val intent = Intent(this, Cremallera::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode que obre l'activitat d'inici quan es fa clic al botó d'inici.
     */
    private fun obrirInici() {
        transportBotoInici.setOnClickListener {
            val intent = Intent(this, Inici::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode que obre l'activitat del Aeri quan es fa clic a la imatge corresponent.
     */
    private fun obrirElAeri() {
        aeriImatges.setOnClickListener {
            val intent = Intent(this, Aeri::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode que obre l'activitat del Funicular de Sant Joan quan es fa clic a la imatge corresponent.
     */
    private fun obrirElFunicularSantJoan() {
        santJoan.setOnClickListener {
            val intent = Intent(this, FunicularSantJoan::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode que obre l'activitat del Funicular de Santa Cova quan es fa clic a la imatge corresponent.
     */
    private fun obrirElFunicularSantaCova() {
        santaCova.setOnClickListener {
            val intent = Intent(this, FunicularSantaCova::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode que executa l'animació de l'avís.
     */
    private fun iniciarAnimacioAvis() {
        // Emmagatzemar l'animació en una variable
        val animacio = AnimationUtils.loadAnimation(this@Transport, R.anim.avis_anim)

        // Aplicar l'animació a l'avís
        transportAvis.startAnimation(animacio)

        // Reiniciar l'animació quan acabi
        animacio.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Esperar un temps abans de tornar a iniciar l'animació
                Handler().postDelayed({
                    iniciarAnimacioAvis()
                }, 6000)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    /**
     * Mètode que agafa l'últim avís de la base de dades.
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
                Log.e("IniciActivity", "Error en agafar les dades", error.toException())
            }
        })
    }

    /**
     * Mètode que indica a l'activitat que es pot tornar enrere.
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Transport, Inici::class.java)
        startActivity(intent)
        finish()
    }
}