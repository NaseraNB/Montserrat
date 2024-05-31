// Paquet
package com.example.cremallerademontserrat.models.models

// Importacions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.cremallerademontserrat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView

// Classe Registre
class Registre : AppCompatActivity() {

    // Variables
    private lateinit var iniciImatge: ImageView
    private lateinit var profile_image: CircleImageView // Imatge de perfil
    private lateinit var registreNom: EditText
    private lateinit var registreEdat: EditText
    private lateinit var registreCorreu: EditText
    private lateinit var registreContraseña: EditText
    private lateinit var registreBoto: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registre)

        // Busca les ID dels components.
        iniciImatge = findViewById(R.id.registreImatge)
        registreNom = findViewById(R.id.registreNom)
        registreEdat = findViewById(R.id.registreEdat)
        registreCorreu = findViewById(R.id.registreCorreu)
        registreContraseña = findViewById(R.id.registreContraseña)
        registreBoto = findViewById(R.id.registreBoto)
        auth = FirebaseAuth.getInstance() // Inicialitza FirebaseAuth
        database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/") // Inicialitza FirebaseDatabase
        reference = database.getReference("USUARIS") // Obté referència a la base de dades


        // Clic dels components.
        iniciImatge.setOnClickListener(){
            obrirMenu()
        }

        // Defineix l'acció del botó de registre
        registreBoto.setOnClickListener {
            val email: String = registreCorreu.text.toString()
            val passE: String = registreContraseña.text.toString()

            // Validació del correu
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                registreCorreu.error = "Correu no vàlid"
            } else if (passE.length < 6) {
                registreContraseña.error = "Contrasenya de menys de 6 caràcters"
            } else {
                RegistrarUsuari(email, passE)
            }
        }
    }

    // Funció per registrar un nou jugador a Firebase Authentication
    private fun RegistrarUsuari(email: String, passw: String) {
        auth.createUserWithEmailAndPassword(email, passw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Autenticació fallida.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Actualitza la interfície d'usuari després del registre
    // Función para actualizar la interfaz de usuario después del registro
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Recuperamos los datos del formulario
            val uidString = user.uid
            val correuString = registreCorreu.text.toString()
            val contrasenyaString = registreContraseña.text.toString()
            val nomString = registreNom.text.toString()
            val edatInt = registreEdat.text.toString().toInt()

            // Creamos un objeto Usuari
            val usuari = Usuari(
                uid = uidString,
                nom = nomString,
                edat = edatInt,
                correu = correuString,
                contrasenya = contrasenyaString
            )

            // Guardamos los datos del jugador en Firebase Realtime Database
            reference.child(uidString).setValue(usuari)
                .addOnSuccessListener {
                    // Si la escritura en la base de datos es exitosa, mostramos un mensaje de éxito
                    Log.i("Firebase", "DocumentSnapshot successfully written!")
                    Toast.makeText(this, "USUARI REGISTRAT", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Registre, Inici::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    // Si ocurre un error al escribir en la base de datos, mostramos un mensaje de error
                    Toast.makeText(this, "Error en la base de dades", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Si el usuario es nulo, mostramos un mensaje de error
            Toast.makeText(this, "Error al crea l'usuari", Toast.LENGTH_SHORT).show()
        }
    }

    // Funció per obrir el menú
    private fun obrirMenu(){
        val dialog = Menu()
        dialog.show(supportFragmentManager, "DialogoMenu")
    }

    // Funció per gestionar el botó enrere
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Registre, Inici_De_Sessio::class.java)
        startActivity(intent)
        finish()
    }
}