package com.example.cremallerademontserrat.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.cremallerademontserrat.R

class Nosaltres : AppCompatActivity() {


    private lateinit var iniciImatge: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nosaltres)

        iniciImatge = findViewById(R.id.iniciImatge)

        obrirMenu()
    }

    private fun obrirMenu() {
        iniciImatge.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Nosaltres, Inici::class.java)
        startActivity(intent)
        finish()
    }
}