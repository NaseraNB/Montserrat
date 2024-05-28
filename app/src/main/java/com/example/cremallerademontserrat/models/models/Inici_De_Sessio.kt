package com.example.cremallerademontserrat.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cremallerademontserrat.R
import com.google.firebase.auth.FirebaseAuth

class Inici_De_Sessio : AppCompatActivity() {

    private lateinit var loginCorreu: EditText
    private lateinit var loginContrasenya: EditText
    private lateinit var loginBotoInciarSessió: Button
    private lateinit var loginRegistre: TextView
    private lateinit var iniciImatge: ImageView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inici_de_sessio)

        // Inicializa FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Busca les ID dels components
        loginCorreu = findViewById(R.id.loginCorreu)
        loginContrasenya = findViewById(R.id.loginContrasenya)
        loginBotoInciarSessió = findViewById(R.id.loginBotoInciarSessió)
        loginRegistre = findViewById(R.id.loginRegistre)
        iniciImatge = findViewById(R.id.iniciImatge)

        // Defineix les accions dels components
        loginRegistre.setOnClickListener {
            anarAlRegistre()
        }

        iniciImatge.setOnClickListener {
            obrirMenu()
        }

        loginBotoInciarSessió.setOnClickListener {
            iniciarSessio()
        }
    }

    private fun iniciarSessio() {
        val email = loginCorreu.text.toString()
        val password = loginContrasenya.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Els camps no poden estar buits", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inici de sessió correcte
                    Toast.makeText(this, "Inici de sessió correcte", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Inici_De_Sessio, Inici::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Si l'inici de sessió falla, mostra un missatge a l'usuari
                    Toast.makeText(this, "Inici de sessió fallit", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun obrirMenu() {
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    private fun anarAlRegistre() {
        val intent = Intent(this, Registre::class.java) // Crea un Intent per canviar a la pantalla.
        startActivity(intent) // Inicia la nova activitat
        finish()  // Tanca aquesta activitat.
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Inici_De_Sessio, Inici_De_Sessio::class.java)
        startActivity(intent)
        finish()
    }
}