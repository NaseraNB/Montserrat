package com.example.cremallerademontserrat.models.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cremallerademontserrat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import android.widget.Toast

class RegistrarAvis : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_avis)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = database.getReference("AVISOS")

        // Registrar avisos
        registrarAvis(1, "El servei de transport a la zona està experimentant retards a causa del trànsit.", "Alert")
        registrarAvis(2, "Entrades descomptades per a grups grans disponibles fins al proper cap de setmana.", "Info")
        registrarAvis(3, "El servei de transport a la zona està experimentant retards a causa del trànsit.", "Info")
        registrarAvis(4, "La seguretat s'ha millorat en les rutes de senderisme després d'una avaluació de riscos.", "Emergency")
    }

    // Clase Avis
    data class Avis(
        var texto: String = "",
        var tipo: String = ""
    )

    private fun registrarAvis(id: Int, text: String, tipus: String) {
        val avis = Avis(
            texto = text,
            tipo = tipus
        )

        reference.child(id.toString()).setValue(avis)
            .addOnSuccessListener {
                // Si la escritura en la base de datos es exitosa, mostramos un mensaje de éxito
                Log.i("Firebase", "DocumentSnapshot successfully written!")
                Toast.makeText(this@RegistrarAvis, "AVIS REGISTRAT", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Si ocurre un error al escribir en la base de datos, mostramos un mensaje de error
                Log.w("Firebase", "Error writing document", e)
                Toast.makeText(this@RegistrarAvis, "ERROR EN LA BASE DE DADES", Toast.LENGTH_SHORT).show()
            }
    }
}
