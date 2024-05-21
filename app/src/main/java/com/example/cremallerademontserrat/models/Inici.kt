package com.example.cremallerademontserrat.models

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.R
import kotlin.random.Random

class Inici : AppCompatActivity() {

    private lateinit var iniciImatge: ImageView
    private lateinit var avis: View
    private lateinit var iniciAvis: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inici)

        iniciImatge = findViewById(R.id.iniciImatge)
        avis = findViewById(R.id.avis)
        iniciAvis = findViewById(R.id.iniciAvis)

        iniciImatge.setOnClickListener {
            obrirMenu()
        }

        iniciAvis.text = "Avis de prova"

        val colors = arrayOf("#FF0000", "#ffdd00", "#0000FF")
        val randomIndex = Random.nextInt(colors.size)
        avis.setBackgroundColor(Color.parseColor(colors[randomIndex]))

    }

    private fun obrirMenu() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
        finish()
    }
}