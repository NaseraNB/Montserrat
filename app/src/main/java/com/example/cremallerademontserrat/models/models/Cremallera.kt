package com.example.cremallerademontserrat.models.models

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cremallerademontserrat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Cremallera
 *
 * @constructor Create empty Cremallera
 */
class Cremallera : AppCompatActivity() {

    private lateinit var botoData: View
    private lateinit var cremalleraBotoBuscar: Button
    private lateinit var dataEscollida: TextView
    private lateinit var botoOrigenFlecha: ImageView
    private lateinit var cremalleraOrigen: TextView
    private lateinit var botoDestiFlecha: ImageView
    private lateinit var cremalleraDesti: TextView
    private lateinit var resultat: View
    private lateinit var cremalleraResultat: TextView
    private lateinit var lineDos: View
    private lateinit var cremalleraDataDos: TextView
    private lateinit var origen_Desti_Llista: View
    private lateinit var textOrigen: TextView
    private lateinit var textDesti: TextView
    private lateinit var viewBlanc: View
    private lateinit var viewGris: View
    private lateinit var recyclerViewOrigen: RecyclerView
    private lateinit var recyclerViewOrigenDos: RecyclerView
    private lateinit var cremalleraComprar: Button
    private lateinit var preuCremalleraUn: TextView
    private lateinit var preuCremalleraDos: TextView
    private lateinit var preuCremalleraTres: TextView
    private lateinit var preuCremalleraCuatro: TextView
    private lateinit var preuCremalleraCinco: TextView
    private lateinit var preuCremalleraSeis: TextView
    private lateinit var andaTornada: View
    private lateinit var titolanadaTornada: TextView
    private lateinit var botoAnadaTornadaFlecha: ImageView
    private lateinit var cremalleraAvis: View
    private lateinit var transportAvis: TextView
    private lateinit var transportMenu: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cremallera)

        botoData = findViewById(R.id.botoData)
        cremalleraBotoBuscar = findViewById(R.id.cremalleraBotoBuscar)
        dataEscollida = findViewById(R.id.cremalleraData)
        botoOrigenFlecha = findViewById(R.id.botoOrigenFlecha)
        cremalleraOrigen = findViewById(R.id.cremalleraOrigen)
        botoDestiFlecha = findViewById(R.id.botoDestiFlecha)
        cremalleraDesti = findViewById(R.id.cremalleraDesti)
        resultat = findViewById(R.id.resultat)
        cremalleraResultat = findViewById(R.id.cremalleraResultat)
        lineDos = findViewById(R.id.lineDos)
        cremalleraDataDos = findViewById(R.id.cremalleraDataDos)
        origen_Desti_Llista = findViewById(R.id.origen_Desti_Llista)
        textOrigen = findViewById(R.id.textOrigen)
        textDesti = findViewById(R.id.textDesti)
        viewBlanc = findViewById(R.id.viewBlanc)
        viewGris = findViewById(R.id.viewGris)
        cremalleraComprar = findViewById(R.id.cremalleraComprar)
        preuCremalleraUn = findViewById(R.id.preuCremalleraUn)
        preuCremalleraDos = findViewById(R.id.preuCremalleraDos)
        preuCremalleraTres = findViewById(R.id.preuCremalleraTres)
        preuCremalleraCuatro = findViewById(R.id.preuCremalleraCuatro)
        preuCremalleraCinco = findViewById(R.id.preuCremalleraCinco)
        preuCremalleraSeis = findViewById(R.id.preuCremalleraSeis)
        andaTornada = findViewById(R.id.andaTornada)
        titolanadaTornada = findViewById(R.id.titolanadaTornada)
        botoAnadaTornadaFlecha = findViewById(R.id.botoAnadaTornadaFlecha)
        transportMenu = findViewById(R.id.transportMenu)

        cremalleraAvis = findViewById(R.id.cremalleraAvis)
        transportAvis = findViewById(R.id.transportAvis)

        amagaVista()

        botoData.setOnClickListener {
            obrirCalendari()
        }

        botoOrigenFlecha.setOnClickListener {
            mostrarOpcions(it)
        }

        botoDestiFlecha.setOnClickListener {
            mostrarOpcionsDos(it)
        }

        cremalleraBotoBuscar.setOnClickListener {
            mostrarResultat()
        }

        botoAnadaTornadaFlecha.setOnClickListener {
            mostrarOpcionesDos(it)
        }

        transportAvis.setOnClickListener(){
            obrirMenu()
        }

        recyclerViewOrigen = findViewById(R.id.recyclerViewOrigen)
        recyclerViewOrigenDos = findViewById(R.id.recyclerViewOrigenDos)
        recyclerViewOrigen.layoutManager = LinearLayoutManager(this)
        recyclerViewOrigenDos.layoutManager = LinearLayoutManager(this)

        // Horarios de llegada a Montserrat desde Monistrol Vila
        val llegadaMontserratDesdeMonistrolVila = listOf("09:15", "10:15", "11:15", "12:15", "13:15", "14:15", "15:15", "16:15", "17:15", "18:15", "19:15", "20:15")

        // Horarios de salida desde Montserrat
        val salidaMontserrat = listOf("09:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "18:30", "19:30")

        // Horarios de llegada a Monistrol Vila desde Montserrat
        val llegadaMonistrolVilaDesdeMontserrat = listOf("09:45", "10:45", "11:45", "12:45", "13:45", "14:45", "15:45", "16:45", "17:45", "18:45", "19:45")

        // Horarios de llegada a Monistrol de Montserrat desde Montserrat
        val llegadaMonistrolDeMontserratDesdeMontserrat = listOf("10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00")


        avis()

    }

    private fun obrirMenu() {
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    private fun mostrarOpcionesDos(view: View) {
        val opciones = PopupMenu(this, view)
        opciones.menuInflater.inflate(R.menu.menu_anada_tornada, opciones.menu)
        opciones.setOnMenuItemClickListener { menuItem ->
            val titulo = menuItem.title.toString()
            titolanadaTornada.text = titulo

            if (titulo == "Anada") {
                preuCremalleraUn.text = "8.40€"
                preuCremalleraDos.text = "4.20€"
                preuCremalleraTres.text = "Gratuït"
                preuCremalleraCuatro.text = "8.40€"
                preuCremalleraCinco.text = "8.40€"
                preuCremalleraSeis.text = "Gratuït"
            } else {
                preuCremalleraUn.text = "21,00€"
                preuCremalleraDos.text = "14,00€"
                preuCremalleraTres.text = "Gratuït"
                preuCremalleraCuatro.text = "21,00€"
                preuCremalleraCinco.text = "14,00€"
                preuCremalleraSeis.text = "Gratuït"
            }
            true
        }
        opciones.show()
    }

    private fun obrirCalendari() {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(this, R.style.DialogTheme, { _, selectedYear, selectedMonth, selectedDay ->
            dataEscollida.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            cremalleraDataDos.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day)
        dialog.show()
    }

    private fun mostrarOpcions(view: View) {
        val opciones = PopupMenu(this, view)
        opciones.menuInflater.inflate(R.menu.menu_origen_desti, opciones.menu)
        opciones.setOnMenuItemClickListener { menuItem ->
            cremalleraOrigen.text = menuItem.title
            true
        }
        opciones.show()
    }


    private fun mostrarOpcionsDos(view: View) {
        val opciones = PopupMenu(this, view)
        opciones.menuInflater.inflate(R.menu.menu_origen_desti, opciones.menu)
        opciones.setOnMenuItemClickListener { menuItem ->
            cremalleraDesti.text = menuItem.title
            true
        }
        opciones.show()
    }

    private fun mostrarResultat() {
        val origenSeleccionado = cremalleraOrigen.text.toString()
        val destinoSeleccionado = cremalleraDesti.text.toString()
        val monistrolMontserrat = resources.getText(R.string.cremmalleraMonistrol)
        val monistrolVilla = resources.getText(R.string.cremmalleraMonistrolDos)

        // Monistrol de Montserrat a Monistrol Vila
        val sortidaMonistrolDeMontserrat = listOf("08:30", "09:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "18:30", "19:30")
        val arribadaMonistrolVila = listOf("08:45", "09:45", "10:45", "11:45", "12:45", "13:45", "14:45", "15:45", "16:45", "17:45", "18:45", "19:45")

        // Monistrol Vila a Monistrol de Montserrat
        val sortidaMonistrolVila = listOf("08:45", "09:45", "10:45", "11:45", "12:45", "13:45", "14:45", "15:45", "16:45", "17:45", "18:45", "19:45")
        val arribadaMonistrolDeMontserrat = listOf("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00")


        val calendar = Calendar.getInstance()
        val today = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        val selectedDate = dataEscollida.text.toString()


        // Calcular la data d'inici i de final de la setmana actual
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = calendar.time
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endOfWeek = calendar.time

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDateParsed = dateFormat.parse(selectedDate)

        // Comprovar si la data seleccionada és dins de la setmana actual
        if (selectedDateParsed == null || selectedDateParsed.before(startOfWeek) || selectedDateParsed.after(endOfWeek)) {
            amagaVista()
            return
        }

        var origenLi = sortidaMonistrolDeMontserrat

        if (origenSeleccionado == monistrolMontserrat && destinoSeleccionado == monistrolVilla) {
            recyclerViewOrigen.adapter = Lista(origenLi, this)
            origenLi = arribadaMonistrolVila
            recyclerViewOrigenDos.adapter = Lista(origenLi, this)
        } else if (origenSeleccionado == monistrolVilla && destinoSeleccionado == monistrolMontserrat) {
            origenLi = sortidaMonistrolVila
            recyclerViewOrigen.adapter = Lista(origenLi, this)
            origenLi = arribadaMonistrolDeMontserrat
            recyclerViewOrigenDos.adapter = Lista(origenLi, this)
        } else {
            amagaVista()
        }

        mostrarVista()
    }

    private fun iniciarAnimacioAvis() {
        val animacio = AnimationUtils.loadAnimation(this@Cremallera, R.anim.avis_anim)
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
                        cremalleraAvis.setBackgroundColor(color)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("IniciActivity", "Error al obtenir les dades", error.toException())
            }
        })
    }

    private fun mostrarVista() {
        resultat.visibility = View.VISIBLE
        cremalleraResultat.visibility = View.VISIBLE
        lineDos.visibility = View.VISIBLE
        cremalleraDataDos.visibility = View.VISIBLE
        origen_Desti_Llista.visibility = View.VISIBLE
        textOrigen.visibility = View.VISIBLE
        textDesti.visibility = View.VISIBLE
        viewBlanc.visibility = View.VISIBLE
        viewGris.visibility = View.VISIBLE
        cremalleraComprar.visibility = View.VISIBLE
    }

    private fun amagaVista() {
        resultat.visibility = View.GONE
        cremalleraResultat.visibility = View.GONE
        lineDos.visibility = View.GONE
        cremalleraDataDos.visibility = View.GONE
        origen_Desti_Llista.visibility = View.GONE
        textOrigen.visibility = View.GONE
        textDesti.visibility = View.GONE
        viewBlanc.visibility = View.GONE
        viewGris.visibility = View.GONE
        cremalleraComprar.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Cremallera, Transport::class.java)
        startActivity(intent)
        finish()
    }
}