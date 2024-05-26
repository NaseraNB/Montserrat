package com.example.cremallerademontserrat.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.R

class Inici_De_Sessio : AppCompatActivity() {

    private lateinit var loginRegistre: TextView
    private lateinit var iniciImatge: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inici_de_sessio)

        loginRegistre = findViewById<TextView>(R.id.loginRegistre)
        iniciImatge = findViewById(R.id.iniciImatge)

        loginRegistre.setOnClickListener() {
            anarAlRegistre()
        }

        iniciImatge.setOnClickListener(){
            obrirMenu()
        }
    }

    private fun obrirMenu(){
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    private fun anarAlRegistre() {
        val intent = Intent(this, Registre::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Inici_De_Sessio, Inici_De_Sessio::class.java)
        startActivity(intent)
        finish()
    }
}