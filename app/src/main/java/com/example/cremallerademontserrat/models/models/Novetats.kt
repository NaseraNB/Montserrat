package com.example.cremallerademontserrat.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cremallerademontserrat.R
import com.google.android.material.imageview.ShapeableImageView

class Novetats : AppCompatActivity() {

    private lateinit var imatgeEscolania: ShapeableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novetats)

        imatgeEscolania = findViewById(R.id.aeriImatges)

        imatgeEscolania.setOnClickListener(){
            obrirNovetat()
        }

    }

    private fun obrirNovetat() {
        val intent = Intent(this, NovetatsInfo::class.java)
        startActivity(intent)
        finish()
    }
}