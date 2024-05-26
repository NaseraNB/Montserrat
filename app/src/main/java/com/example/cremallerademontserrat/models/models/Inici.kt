package com.example.cremallerademontserrat.models.models

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cremallerademontserrat.R
import kotlin.random.Random

class Inici : AppCompatActivity() {

    private lateinit var iniciImatge: ImageView
    private lateinit var avis: View
    private lateinit var iniciAvis: TextView
    private lateinit var iniciBotoInici: TextView
    private lateinit var iniciBotoTransport: TextView
    private lateinit var liniaInici: View
    private lateinit var novetatImatge: ImageView
    private lateinit var novetatTitol: TextView
    private lateinit var novetatData: TextView


    private val carouselItems = listOf(
        CarouselItem(R.drawable.imagen_uno, "Título 1", "20/05/2024"),
        CarouselItem(R.drawable.imagen_dos, "Escolania de Montserrat", "21/05/2024"),
        CarouselItem(R.drawable.imagen_tres, "Museu Montserrat", "22/05/2024")
    )
    private var currentCarouselIndex = 0
    private val carouselHandler = Handler(Looper.getMainLooper())
    private val carouselRunnable = object : Runnable {
        override fun run() {
            updateCarousel()
            carouselHandler.postDelayed(this, 10000) // Cambia cada 10 segundos
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inici)

        iniciImatge = findViewById(R.id.iniciImatge)
        avis = findViewById(R.id.avis)
        iniciAvis = findViewById(R.id.iniciAvis)
        iniciBotoInici = findViewById(R.id.iniciBotoInici)
        iniciBotoTransport = findViewById(R.id.iniciBotoTransport)
        liniaInici = findViewById(R.id.liniaInici)
        novetatImatge = findViewById(R.id.novetatImatge)
        novetatTitol = findViewById(R.id.novetatTitol)
        novetatData = findViewById(R.id.novetatData)


        iniciImatge.setOnClickListener {
            obrirMenu()
        }

        iniciAvis.text = "Avis de prova"

        val colors = arrayOf("#FF0000", "#ffdd00", "#0000FF")
        val randomIndex = Random.nextInt(colors.size)
        avis.setBackgroundColor(Color.parseColor(colors[randomIndex]))

        botoInici()
        botoTransport()

        // Iniciar el carrusel
        carouselHandler.post(carouselRunnable)

        novetatImatge.setOnClickListener(){
            obrirNovetats()
        }
    }

    private fun obrirMenu() {
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    private fun botoInici() {
        iniciBotoInici.setOnClickListener {
            obrirInici()
        }
    }

    private fun obrirInici() {
        val intent = Intent(this, Inici::class.java)
        startActivity(intent)
        finish()
    }

    private fun botoTransport() {
        iniciBotoTransport.setOnClickListener {
            obrirTransport()
        }
    }

    private fun obrirTransport() {
        val intent = Intent(this, Transport::class.java)
        startActivity(intent)
        finish()
    }

    private fun obrirNovetats() {
        val intent = Intent(this, Novetats::class.java)
        startActivity(intent)
        finish()
    }


    private fun updateCarousel() {
        val item = carouselItems[currentCarouselIndex]
        novetatImatge.setImageResource(item.imageResId)
        novetatTitol.text = item.title
        novetatData.text = item.date

        currentCarouselIndex = (currentCarouselIndex + 1) % carouselItems.size
    }

    override fun onStart() {
        super.onStart()
        carouselHandler.post(carouselRunnable)
    }

    override fun onStop() {
        super.onStop()
        carouselHandler.removeCallbacks(carouselRunnable)
    }
}
