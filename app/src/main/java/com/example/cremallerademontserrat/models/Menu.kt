package com.example.cremallerademontserrat.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.PopupMenu
import com.example.cremallerademontserrat.R

class Menu : AppCompatActivity() {

    private lateinit var menuTanca: TextView
    private lateinit var menuFlecha: ImageView
    private lateinit var menuRutes: TextView
    private lateinit var menuInici: TextView
    private lateinit var menuTransport: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        menuTanca = findViewById(R.id.menuTancarSessio)
        menuFlecha = findViewById(R.id.menuFlecha)
        menuRutes = findViewById(R.id.menuRutes)
        menuInici = findViewById(R.id.menuInici)
        menuTransport = findViewById(R.id.menuTransport)

        menuTanca.setOnClickListener {
            obrirTancar()
        }

        menuFlecha.setOnClickListener {
            mostrarOpcions()
        }

        menuRutes.setOnClickListener() {
            obrirRutes()
        }

        menuInici.setOnClickListener() {
            obrirInici()
        }

        menuTransport.setOnClickListener(){
            obrirTransport()
        }



    }

    private fun obrirTancar() {
        val intent = Intent(this, Inici_De_Sessio::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    private fun obrirRutes() {
        val intent = Intent(this, Rutes::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    private fun obrirInici() {
        val intent = Intent(this, Inici::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    private fun obrirTransport() {
        val intent = Intent(this, Transport::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    private fun mostrarOpcions() {
        val menu = PopupMenu(this, menuFlecha)
        menu.menuInflater.inflate(R.menu.menu_array, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.tren -> {
                    // Acción para "Tren"

                    true
                }
                R.id.aeri -> {
                    // Acción para "Aeri"
                    true
                }
                R.id.funicular -> {
                    // Acción para "Funicular"
                    true
                }
                else -> false
            }
        }
        menu.show()
    }
}
