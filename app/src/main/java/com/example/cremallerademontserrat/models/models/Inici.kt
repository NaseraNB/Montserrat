// Paquet
package com.example.cremallerademontserrat.models.models
// Importacions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cremallerademontserrat.R
import kotlin.random.Random

// Classe
class Inici : AppCompatActivity() {

    // Variables
    private lateinit var iniciImatge: ImageView
    private lateinit var avis: View
    private lateinit var iniciAvis: TextView
    private lateinit var iniciBotoInici: TextView
    private lateinit var iniciBotoTransport: TextView
    private lateinit var liniaInici: View
    private lateinit var novetatImatge: ImageView
    private lateinit var novetatTitol: TextView
    private lateinit var novetatData: TextView
    private lateinit var iniciReserveDescripcio: TextView
    private lateinit var reservaView: View


    // Llista de carrusel
    private val carouselItems = listOf(
        CarouselItem(R.drawable.imagen_uno, "Título 1", "20/05/2024"),
        CarouselItem(R.drawable.imagen_dos, "Escolania de Montserrat", "21/05/2024"),
        CarouselItem(R.drawable.imagen_tres, "Museu Montserrat", "22/05/2024")
    )
    private var currentCarouselIndex = 0
    private val carouselHandler = Handler(Looper.getMainLooper())
    private val carouselRunnable = object : Runnable {
        override fun run() {
            actualitzarCarrusel()
            carouselHandler.postDelayed(this, 50000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inici)

        Log.d("IniciActivity", "onCreate called")

        // Buscar les ID dels components.
        iniciImatge = findViewById(R.id.iniciImatge)
        avis = findViewById(R.id.avis)
        iniciAvis = findViewById(R.id.iniciAvis)
        iniciBotoInici = findViewById(R.id.iniciBotoInici)
        iniciBotoTransport = findViewById(R.id.iniciBotoTransport)
        liniaInici = findViewById(R.id.liniaInici)
        novetatImatge = findViewById(R.id.novetatImatge)
        novetatTitol = findViewById(R.id.novetatTitol)
        novetatData = findViewById(R.id.novetatData)
        iniciReserveDescripcio = findViewById(R.id.iniciReserveDescripcio)
        reservaView = findViewById(R.id.constraintLayout2)

        // Mètodes que s'executa en inici
        botoMenu()
        dadesDelCarrusel()
        botoInici()
        botoTransport()
        botoNovetats()
        canviarDescripcioReserva()
        botoReserva()

    }

    // Mètode que anomena un altre mètode que anomena activitat.
    private fun botoMenu() {
        iniciImatge.setOnClickListener {
            obrirMenu()
        }
    }

    // Mètode que ens obre el menú
    private fun obrirMenu() {
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    // Mètode que canvia l'avís
    private fun dadesDelCarrusel(){
        iniciAvis.text = "Avis de prova"

        val colors = arrayOf("#FF0000", "#ffdd00", "#0000FF")
        val randomIndex = Random.nextInt(colors.size)
        avis.setBackgroundColor(Color.parseColor(colors[randomIndex]))

        // Iniciar el carrusel
        carouselHandler.post(carouselRunnable)
    }

    // Mètode que anomena un altre mètode que anomena activitat.
    private fun botoInici() {
        iniciBotoInici.setOnClickListener {
            obrirInici()
        }
    }

    // Mètode que canvia d'activitat
    private fun obrirInici() {
        val intent = Intent(this, Inici::class.java)
        startActivity(intent)
        finish()
    }

    // Mètode que anomena un altre mètode que anomena activitat.
    private fun botoTransport() {
        iniciBotoTransport.setOnClickListener {
            obrirTransport()
        }
    }

    // Mètode que canvia d'activitat
    private fun obrirTransport() {
        val intent = Intent(this, Transport::class.java)
        startActivity(intent)
        finish()
    }

    // Mètode que canvia la descripció de la reserva.
    private fun canviarDescripcioReserva(){
        iniciReserveDescripcio.text = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout."
    }

    // Mètode que anomena un altre mètode que anomena activitat.
    private fun botoNovetats() {
        novetatImatge.setOnClickListener(){
            obrirNovetats()
        }
    }

    // Mètode que canvia d'activitat
    private fun obrirNovetats() {
        val intent = Intent(this, Novetats::class.java)
        startActivity(intent)
        finish()
    }

    // Mètode que anomena un altre mètode que anomena activitat.
    private fun botoReserva() {
        reservaView.setOnClickListener(){
            obrirReserva()
        }
    }

    // Mètode que canvia d'activitat
    private fun obrirReserva() {
        val intent = Intent(this, NovetatsInfo::class.java)
        startActivity(intent)
        finish()
    }

    // Mètode que actualitza el carrusel
    private fun actualitzarCarrusel() {
        val item = carouselItems[currentCarouselIndex]
        novetatImatge.setImageResource(item.imageResId)
        novetatTitol.text = item.title
        novetatData.text = item.date

        currentCarouselIndex = (currentCarouselIndex + 1) % carouselItems.size
    }

    // Mètodes heretats que s'utilitza al carrusel
    override fun onStart() {
        super.onStart()
        carouselHandler.post(carouselRunnable)
    }

    override fun onStop() {
        super.onStop()
        carouselHandler.removeCallbacks(carouselRunnable)
    }
}