/**
 * Paquet per a les classes relacionades amb l'aplicació.
 */
package com.example.cremallerademontserrat.models.models

// Importacions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cremallerademontserrat.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activitat d'inici de l'aplicació, que mostra diversa informació i funcionalitats.
 */
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
    private lateinit var iniciTipus: TextView
    private lateinit var iniciIcon: ImageView
    private lateinit var iniciTemp: TextView
    private lateinit var iniciAvui: TextView
    private lateinit var reservaImatge: ShapeableImageView
    private lateinit var iniciReserveTitol: TextView
    private lateinit var iniciReservaData: TextView

    // Firebase
    private val database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")

    // Llista de carrusel
    private val carouselItems = listOf(
        CarouselItem(R.drawable.imagen_dos, "Escolania de Montserrat", "20/05/2024"),
        CarouselItem(R.drawable.santuarimontserrat, "Santuari de Montserrat", "21/05/2024"),
        CarouselItem(R.drawable.imagen_tres, "Museu Montserrat", "22/05/2024")
    )
    private var currentCarouselIndex = 0
    private val carouselHandler = Handler(Looper.getMainLooper())
    private val carouselRunnable = object : Runnable {
        override fun run() {
            actualitzarCarrusel()
            carouselHandler.postDelayed(this, 7000)
        }
    }

    // Mètode principal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inici)


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
        iniciTipus = findViewById(R.id.iniciTipus)
        iniciIcon = findViewById(R.id.iniciIcon)
        iniciTemp = findViewById(R.id.iniciTemp)
        iniciAvui = findViewById(R.id.textView4)
        reservaImatge = findViewById(R.id.reservaImatge)
        iniciReserveTitol = findViewById(R.id.iniciReserveTitol)
        iniciReservaData = findViewById(R.id.iniciReservaData)

        // Mètodes que s'executa en inici
        obrirMenu()
        dadesDelCarrusel()
        botoInici()
        botoTransport()
        botoNovetats()
        botoReserva()
        obtenirClima()
        avis()
        obtenirIMostraNovetat()
    }

    // Mètode per obtenir la novetat de la base de dades
    private fun obtenirIMostraNovetat() {
        val reference = database.getReference("novetats")

        reference.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (novetatSnapshot in snapshot.children) {

                        // Agafem les dades de la base de dades
                        val titulo = novetatSnapshot.child("nom").getValue(String::class.java)
                        val descripcion = novetatSnapshot.child("descripcio").getValue(String::class.java)
                        val fechaInicio = novetatSnapshot.child("data").getValue(String::class.java)
                        val imagen = novetatSnapshot.child("imatge").getValue(String::class.java)

                        // Mostra les dades de la novetat
                        iniciReserveTitol.text = titulo
                        iniciReservaData.text = fechaInicio
                        iniciReserveDescripcio.text = descripcion
                        Glide.with(this@Inici)
                            .load(imagen)
                            .into(reservaImatge)
                    }
                }
            }

            // Missatge d'error
            override fun onCancelled(error: DatabaseError) {
                Log.e("IniciActivity", "Error en agafar les dades", error.toException())
            }
        })
    }


    // Mètode que anomena un altre mètode que anomena activitat.
    private fun obrirMenu() {
        iniciImatge.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    // Mètode que inicia el carrusel
    private fun dadesDelCarrusel(){
        carouselHandler.post(carouselRunnable)
    }

    // Mètode que actualitza el carrusel
    private fun actualitzarCarrusel() {
        val item = carouselItems[currentCarouselIndex]
        novetatImatge.setImageResource(item.imageResId)
        novetatTitol.text = item.title
        novetatData.text = item.date

        currentCarouselIndex = (currentCarouselIndex + 1) % carouselItems.size
    }

    // Mètode que executa l'animació de l'avís
    private fun iniciarAnimacioAvis() {
        // Emmagatzemar l'animació en una variable
        val animacio = AnimationUtils.loadAnimation(this@Inici, R.anim.avis_anim)

        // Aplicar l'animació a l'avís
        iniciAvis.startAnimation(animacio)

        // Reinicieu l'animació quan acabi
        animacio.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Espereu un temps abans de tornar a iniciar l'animació
                Handler().postDelayed({
                    iniciarAnimacioAvis()
                }, 6000)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    // Mètode que agafa l'últim avís de la base de dades
    private fun avis() {
        // Taula
        val reference = database.getReference("AVISOS")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Verificar si hi ha dades
                if (snapshot.exists()) {
                    for (avisSnapshot in snapshot.children) {

                        // Obtenir els dades i emmagatzemar-los en variables
                        val texto = avisSnapshot.child("texto").getValue(String::class.java)
                        val tipo = avisSnapshot.child("tipo").getValue(String::class.java)

                        // Mostra l'avís
                        iniciAvis.text = texto

                        // Inicieu l'animació
                        iniciarAnimacioAvis()

                        // Assignar color segons el tipus d'avís
                        val color = when (tipo) {
                            "Info" -> Color.BLUE
                            "Emergency" -> Color.RED
                            "Alert" -> Color.YELLOW
                            else -> Color.WHITE
                        }

                        // Establir el color de fons de l'Avís
                        avis.setBackgroundColor(color)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("IniciActivity", "Error en agafar les dades", error.toException())
            }
        })
    }


    // Mètode de canviar a una altra activitat
    private fun botoInici() {
        iniciBotoInici.setOnClickListener {
            val intent = Intent(this, Inici::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun botoTransport() {
        iniciBotoTransport.setOnClickListener {
            val intent = Intent(this, Transport::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun botoNovetats() {
        novetatImatge.setOnClickListener(){
            val intent = Intent(this, Novetats::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun botoReserva() {
        reservaView.setOnClickListener(){
            val intent = Intent(this, NovetatsInfo::class.java)
            startActivity(intent)
            finish()
        }
    }


    // Mètode que agafa les dades d´una api del temps per saber el temps de montserrat
    private fun obtenirClima() {

        // La clau, la ubicació i l'api
        val apiKey = "d063deacb21c82b30fb600389667cf8e"
        val latitude = 41.60059809245593
        val longitude = 1.8295780980270278
        val apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=$apiKey&units=metric&lang=ca"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = URL(apiUrl).readText()
                Log.d("IniciActivity", "API Response: $response")
                withContext(Dispatchers.Main) {

                    // Agafem les dades

                    val jsonObj = JSONObject(response)
                    val main = jsonObj.getJSONObject("main")
                    val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                    val temperature = main.getString("temp") + "°C"
                    val weatherCondition = weather.getString("main")
                    val weatherDescription = weather.getString("description")
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = dateFormat.format(Date())


                    // Mostrem les dades
                    iniciTemp.text = temperature
                    iniciTipus.text = when (weatherCondition) {
                        "Clear" -> "Assolellat"
                        "Clouds" -> "Ennuvolat"
                        "Rain" -> "Plujós"
                        else -> weatherDescription
                    }

                    iniciIcon.setImageResource(
                        when (weatherCondition) {
                            "Clear" -> R.drawable.sol
                            "Clouds" -> R.drawable.nuvol
                            "Rain" -> R.drawable.pluja
                            else -> R.drawable.idioma
                        }
                    )

                    iniciAvui.text = date
                }
            } catch (e: Exception) {
                Log.e("IniciActivity", "Error en agafar les dades", e)
                e.printStackTrace()
            }
        }
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

    // Mètode que indica a l'activitat que podeu tornar
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Inici, Inici::class.java)
        startActivity(intent)
        finish()
    }

}
