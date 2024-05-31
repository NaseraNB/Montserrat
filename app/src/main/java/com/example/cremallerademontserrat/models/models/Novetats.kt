package com.example.cremallerademontserrat.models.models

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cremallerademontserrat.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Novetats : AppCompatActivity() {

    // Variables
    private lateinit var menuT: ImageView
    private lateinit var novetatEscolaniaImatges: ShapeableImageView
    private lateinit var escolaniaData: TextView
    private lateinit var fletxaNovetatsEsquerraEscolania: ImageView
    private lateinit var fletxaNovetatsDretaEscolania: ImageView
    private lateinit var novetatsSantuariImatge: ShapeableImageView
    private lateinit var fletxaNovetatsDretaSantuari: ImageView
    private lateinit var fletxaNovetatsEsquerraSantuari: ImageView
    private lateinit var novetatsAmicsImatge: ShapeableImageView
    private lateinit var fletxaNovetatsDretaAmics: ImageView
    private lateinit var fletxaNovetatsEsquerraAmics: ImageView
    private lateinit var santuariDataDos: TextView
    private lateinit var amicsDataTres: TextView
    private lateinit var iniciAvis: TextView
    private lateinit var avis: View
    private lateinit var transportBotoInici: TextView
    private lateinit var textView10: TextView


    var database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")
    var reference = database.getReference("novetats")

    // ArrayList para almacenar novedades
    private lateinit var escolaniaList: ArrayList<NovetatsBBDD>
    private lateinit var santuariList: ArrayList<NovetatsBBDD>
    private lateinit var amicsList: ArrayList<NovetatsBBDD>
    private var currentIndexEscolania: Int = 0
    private var currentIndexSantuari: Int = 0
    private var currentIndexAmics: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novetats)

        menuT = findViewById(R.id.menuT)
        novetatEscolaniaImatges = findViewById(R.id.novetatEscolaniaImatges)
        escolaniaData = findViewById(R.id.escolaniaData)
        fletxaNovetatsEsquerraEscolania = findViewById(R.id.fletxaNovetatsEsquerraEscolania)
        fletxaNovetatsDretaEscolania = findViewById(R.id.fletxaNovetatsDretaEscolania)

        novetatsSantuariImatge = findViewById(R.id.novetatsSantuariImatge)
        fletxaNovetatsDretaSantuari = findViewById(R.id.fletxaNovetatsDretaSantuari)
        fletxaNovetatsEsquerraSantuari = findViewById(R.id.fletxaNovetatsEsquerraSantuari)
        santuariDataDos = findViewById(R.id.santuariDataDos)

        novetatsAmicsImatge = findViewById(R.id.novetatsAmicsImatge)
        fletxaNovetatsDretaAmics = findViewById(R.id.fletxaNovetatsDretaAmics)
        fletxaNovetatsEsquerraAmics = findViewById(R.id.fletxaNovetatsEsquerraAmics)
        amicsDataTres = findViewById(R.id.amicsDataTres)
        iniciAvis = findViewById(R.id.iniciAvis)
        avis = findViewById(R.id.avis)
        transportBotoInici = findViewById(R.id.transportBotoInici)
        textView10 = findViewById(R.id.textView10)

        // Inicializar el arrayList
        escolaniaList = ArrayList()
        santuariList = ArrayList()
        amicsList = ArrayList()


        obtenerDatosFirebase()
        obrirMenu()
        avis()
        botoInici()
        botoTransport()

        novetatEscolaniaImatges.setOnClickListener {
            abrirNovetatsInfo(escolaniaList[currentIndexEscolania])
        }

        novetatsSantuariImatge.setOnClickListener {
            abrirNovetatsInfo(santuariList[currentIndexSantuari])
        }

        novetatsAmicsImatge.setOnClickListener {
            abrirNovetatsInfo(amicsList[currentIndexAmics])
        }


    }

    // Método para mostrar una novedad según el índice proporcionado
    private fun mostrarNovedad(index: Int, list: ArrayList<NovetatsBBDD>, dataTextView: TextView, imageView: ShapeableImageView) {
        val novedad = list[index]
        dataTextView.text = "${novedad.data}"

        // Cargar la imagen
        Glide.with(this@Novetats)
            .load(novedad.imatge)
            .into(imageView)
    }

    // Método que abre el diálogo del menú
    private fun obrirMenu() {
        menuT.setOnClickListener {
            val dialog = Menu()
            dialog.show(supportFragmentManager, "DialogoMenu")
        }
    }

    // Método para obtener los datos de Firebase
    private fun obtenerDatosFirebase() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                escolaniaList.clear()
                santuariList.clear()
                amicsList.clear()

                for (snapshot in dataSnapshot.children) {
                    val novetat = snapshot.getValue(NovetatsBBDD::class.java)

                    when (novetat?.user_id) {
                        "1" -> escolaniaList.add(novetat)
                        "2" -> santuariList.add(novetat)
                        "3" -> amicsList.add(novetat)
                    }
                }

                if (escolaniaList.isNotEmpty()) {
                    currentIndexEscolania = 0
                    mostrarNovedad(currentIndexEscolania, escolaniaList, escolaniaData, novetatEscolaniaImatges)
                }

                if (santuariList.isNotEmpty()) {
                    currentIndexSantuari = 0
                    mostrarNovedad(currentIndexSantuari, santuariList, santuariDataDos, novetatsSantuariImatge)
                }

                if (amicsList.isNotEmpty()) {
                    currentIndexAmics = 0
                    mostrarNovedad(currentIndexAmics, amicsList, amicsDataTres, novetatsAmicsImatge)
                }

                // Configurar listeners para cada categoría
                setupCategoryListeners()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Maneja los errores de cancelación aquí
            }
        })
    }

    private fun setupCategoryListeners() {
        // Configurar el listener de las flechas para la categoría "Escolania"
        fletxaNovetatsEsquerraEscolania.setOnClickListener {
            if (currentIndexEscolania > 0) {
                currentIndexEscolania--
                mostrarNovedad(currentIndexEscolania, escolaniaList, escolaniaData, novetatEscolaniaImatges)
            }
        }

        fletxaNovetatsDretaEscolania.setOnClickListener {
            if (currentIndexEscolania < escolaniaList.size - 1) {
                currentIndexEscolania++
                mostrarNovedad(currentIndexEscolania, escolaniaList, escolaniaData, novetatEscolaniaImatges)
            }
        }

        // Configurar el listener de las flechas para la categoría "Santuari"
        fletxaNovetatsEsquerraSantuari.setOnClickListener {
            if (currentIndexSantuari > 0) {
                currentIndexSantuari--
                mostrarNovedad(currentIndexSantuari, santuariList, santuariDataDos, novetatsSantuariImatge)
            }
        }

        fletxaNovetatsDretaSantuari.setOnClickListener {
            if (currentIndexSantuari < santuariList.size - 1) {
                currentIndexSantuari++
                mostrarNovedad(currentIndexSantuari, santuariList, santuariDataDos, novetatsSantuariImatge)
            }
        }

        // Configurar el listener de las flechas para la categoría "Amics"
        fletxaNovetatsEsquerraAmics.setOnClickListener {
            if (currentIndexAmics > 0) {
                currentIndexAmics--
                mostrarNovedad(currentIndexAmics, amicsList, amicsDataTres, novetatsAmicsImatge)
            }
        }

        fletxaNovetatsDretaAmics.setOnClickListener {
            if (currentIndexAmics < amicsList.size - 1) {
                currentIndexAmics++
                mostrarNovedad(currentIndexAmics, amicsList, amicsDataTres, novetatsAmicsImatge)
            }
        }
    }

    // Método para abrir la actividad NovetatsInfo y enviar los datos correspondientes
    private fun abrirNovetatsInfo(novedad: NovetatsBBDD) {
        val intent = Intent(this, NovetatsInfo::class.java)
        intent.putExtra("titulo", novedad.nom)
        intent.putExtra("imagen", novedad.imatge)
        intent.putExtra("fechaInicio", novedad.data)
        intent.putExtra("fechaFinal", "")
        intent.putExtra("descripcion", novedad.descripcio)
        intent.putExtra("novedadId", novedad.id)
        startActivity(intent)
    }

    // Mètode que executa l'animació de l'avís
    private fun iniciarAnimacioAvis() {
        // Emmagatzemar l'animació en una variable
        val animacio = AnimationUtils.loadAnimation(this@Novetats, R.anim.avis_anim)

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

    private fun botoInici() {
        transportBotoInici.setOnClickListener {
            val intent = Intent(this, Inici::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun botoTransport() {
        textView10.setOnClickListener {
            val intent = Intent(this, Transport::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Método que vuelve a la pantalla anterior
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Novetats, Inici::class.java)
        startActivity(intent)
        finish()
    }
}