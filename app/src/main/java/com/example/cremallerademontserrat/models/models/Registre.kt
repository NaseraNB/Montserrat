package com.example.cremallerademontserrat.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.cremallerademontserrat.R

class Registre : AppCompatActivity() {

    private lateinit var iniciImatge: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registre)

        iniciImatge = findViewById(R.id.registreImatge)

        iniciImatge.setOnClickListener(){
            obrirMenu()
        }

    }

    private fun obrirMenu(){
        val intent = Intent(this, Menu::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Registre, Inici_De_Sessio::class.java)
        startActivity(intent)
        finish()
    }
}