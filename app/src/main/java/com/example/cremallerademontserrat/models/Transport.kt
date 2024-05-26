package com.example.cremallerademontserrat.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.cremallerademontserrat.R
import com.google.android.material.imageview.ShapeableImageView

class Transport : AppCompatActivity() {

    private lateinit var transportMenu: ImageView
    private lateinit var imageCremallera: ShapeableImageView
    private lateinit var transportBotoInici: TextView
    private lateinit var aeriImatges: ShapeableImageView
    private lateinit var santJoan: ShapeableImageView
    private lateinit var santaCova: ShapeableImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)

        transportMenu = findViewById(R.id.transportMenu)
        imageCremallera = findViewById(R.id.imageCremallera)
        transportBotoInici = findViewById(R.id.transportBotoInici)
        aeriImatges = findViewById(R.id.aeriImatges)
        santJoan = findViewById(R.id.imageView4)
        santaCova = findViewById(R.id.imageView3)

        transportMenu.setOnClickListener(){
            obrirMenu()
        }

        imageCremallera.setOnClickListener(){
            obrirElCremallera()
        }

        transportBotoInici.setOnClickListener(){
            obrirInici()
        }

        aeriImatges.setOnClickListener(){
            obrirElAeri()
        }

        santJoan.setOnClickListener(){
            obrirElFunicularSantJoan()
        }

        santaCova.setOnClickListener() {
            obrirElFunicularSantaCova()
        }

    }

    private fun obrirMenu() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
        finish()
    }

    private fun obrirElCremallera() {
        val intent = Intent(this, Cremallera::class.java)
        startActivity(intent)
        finish()
    }

    private fun obrirInici() {
        val intent = Intent(this, Inici::class.java)
        startActivity(intent)
        finish()
    }

    private fun obrirElAeri() {
        val intent = Intent(this, Aeri::class.java)
        startActivity(intent)
        finish()
    }

    private fun obrirElFunicularSantJoan() {
        val intent = Intent(this, FunicularSantJoan::class.java)
        startActivity(intent)
        finish()
    }

    private fun obrirElFunicularSantaCova() {
        val intent = Intent(this, FunicularSantaCova::class.java)
        startActivity(intent)
        finish()
    }

}