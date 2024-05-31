/**
 * Paquet per a les classes relacionades amb l'aplicació.
 */
package com.example.cremallerademontserrat.models.models

// Importacions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cremallerademontserrat.R
import android.content.Intent
import android.os.Handler

/**
 * Activitat de Splash que es mostra quan s'inicia l'aplicació.
 */
class Splash : AppCompatActivity() {

    // Variables
    private val DURACIO_SPLASH: Long = 3000 // Duració del splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Crida a la funció per canviar d'activitat
        canviarActivity()
    }

    /**
     * Funció que canvia a l'activitat següent després del temps especificat.
     */
    private fun canviarActivity() {
        Handler().postDelayed({

            // Crea un Intent per canviar a la següent pantalla.
            val intent = Intent(this, Inici::class.java)

            // Inicia la nova activitat
            startActivity(intent)

            // Tanca aquesta activitat.
            finish()

            // Estableix el temps de retard abans de canviar de pantalla.
        }, DURACIO_SPLASH)
    }

    // Permet que l'usuari no surti de l'splash fins que finalitzin
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Splash, Splash::class.java)
        startActivity(intent)
        finish()
    }
}
