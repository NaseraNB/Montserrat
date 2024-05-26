package com.example.cremallerademontserrat.models.models

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

class Rutes : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var rutesImatge: ImageView
    private lateinit var avis: View
    private lateinit var rutesAvis: TextView
    private lateinit var mapa: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutes)

        rutesImatge = findViewById(R.id.rutesImatge)
        avis = findViewById(R.id.avis)
        rutesAvis = findViewById(R.id.rutesAvis)

        val mapaFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapaFragment.getMapAsync(this)

        rutesImatge.setOnClickListener {
            obrirMenu()
        }

        rutesAvis.text = "Avis de prova"

        val colors = arrayOf("#FF0000", "#ffdd00", "#0000FF")
        val randomIndex = Random.nextInt(colors.size)
        avis.setBackgroundColor(Color.parseColor(colors[randomIndex]))
    }

    private fun obrirMenu() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap

        // Configura la ubicación inicial del mapa
        val montserrat = LatLng(41.5912, 1.8371)
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(montserrat, 15f))

        // Añade un marcador en Montserrat
        mapa.addMarker(MarkerOptions().position(montserrat).title("Montserrat"))
    }
}
