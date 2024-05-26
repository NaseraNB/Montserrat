// Paquet
package com.example.cremallerademontserrat.models

// Importacions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cremallerademontserrat.R
import android.content.Intent
import android.os.Handler
import android.view.WindowManager

// Classe principal
class Splash : AppCompatActivity() {

    // Variables
    private val DURACIO_SPLASH: Long = 3000 // Duració del splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Crida a la funció per canviar d'activitat
        canviarActivity()
    }

    // Mètode que canvia d'activitat.
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