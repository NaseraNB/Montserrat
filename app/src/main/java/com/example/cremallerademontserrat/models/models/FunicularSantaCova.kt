package com.example.cremallerademontserrat.models.models

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FunicularSantaCova : AppCompatActivity() {

    private lateinit var funicularMenu: ImageView
    private lateinit var transportAvis: TextView
    private lateinit var avis: View
    private lateinit var funicularSantJoanInfo: Button
    private lateinit var cremalleraComprar: Button
    private lateinit var transportBotoTransport: TextView
    private lateinit var transportBotoInici: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funicular_santa_cova)

        funicularMenu = findViewById(R.id.funicularMenu)
        transportAvis = findViewById(R.id.transportAvis)
        avis = findViewById(R.id.avis)
        funicularSantJoanInfo = findViewById(R.id.funicularSantJoanInfo)
        cremalleraComprar = findViewById(R.id.button3)
        transportBotoTransport = findViewById(R.id.textView10)
        transportBotoInici = findViewById(R.id.transportBotoInici)

        obrirMenu()
        funicularSantJoanInfo()
        cremalleraComprar()
        transportBotoTransport()
        transportBotoInici()
        avis()

    }

    /**
     * Mètode per obrir el menú.
     */
    private fun obrirMenu() {
        funicularMenu.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    private fun funicularSantJoanInfo() {
        funicularSantJoanInfo.setOnClickListener {
            val url = "https://turistren.cat/es/trenes/cremallera-y-funiculares-de-montserrat/?_gl=1*t03a4g*_up*MQ..*_ga*MTY0NjYxNDExMi4xNzE3MTA2Mjcw*_ga_D8LR3VYWV8*MTcxNzEwNjI2OS4xLjEuMTcxNzEwNjI4My4wLjAuMA..#horariosytarifas"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun cremalleraComprar() {
        cremalleraComprar.setOnClickListener {
            val url = "https://turistren.cat/es/trenes/cremallera-y-funiculares-de-montserrat/?_gl=1*t03a4g*_up*MQ..*_ga*MTY0NjYxNDExMi4xNzE3MTA2Mjcw*_ga_D8LR3VYWV8*MTcxNzEwNjI2OS4xLjEuMTcxNzEwNjI4My4wLjAuMA..#horariosytarifas"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun transportBotoTransport() {
        transportBotoTransport.setOnClickListener(){
            val intent = Intent(this, Transport::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun transportBotoInici() {
        transportBotoInici.setOnClickListener(){
            val intent = Intent(this, Inici::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Mètode per iniciar l'animació dels avisos.
     */
    private fun iniciarAnimacioAvis() {
        val animacio = AnimationUtils.loadAnimation(this@FunicularSantaCova, R.anim.avis_anim)
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@FunicularSantaCova, Transport::class.java)
        startActivity(intent)
        finish()
    }
}