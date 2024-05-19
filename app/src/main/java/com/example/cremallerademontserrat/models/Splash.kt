package com.example.cremallerademontserrat.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cremallerademontserrat.R
import android.content.Intent
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.models.Inici_De_Sessio

class Splash : AppCompatActivity() {

    // Variables
    private val DURACIO_SPLASH: Long = 4000 // Duració del splash
    private lateinit var animacioSuperior: Animation // Animació de la part superior
    private lateinit var animacioInferior: Animation // Animació de la part inferior
    private lateinit var imatge: ImageView // Imatge del Splash
    private lateinit var titol: TextView // Títol del Splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        // Carrega les animacions
        animacioSuperior = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        animacioInferior = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        // Inicialitza els components amb les variables
        imatge = findViewById(R.id.imatgeSplash)
        titol = findViewById(R.id.titolSplash)

        // Assigna les animacions als components
        imatge.animation = animacioSuperior
        titol.animation = animacioInferior

        // Crida a la funció per canviar d'activitat
        canviarActivity()
    }

    private fun canviarActivity() {
        Handler().postDelayed({

            // Crea un Intent per canviar a la pantalla.
            val intent = Intent(this, Inici_De_Sessio::class.java)

            // Inicia la nova activitat
            startActivity(intent)

            // Tanca aquesta activitat.
            finish()

            // Estableix el temps de retard abans de canviar de pantalla.
        }, DURACIO_SPLASH)
    }
}