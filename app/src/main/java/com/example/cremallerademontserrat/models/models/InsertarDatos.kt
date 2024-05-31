package com.example.cremallerademontserrat.models.models

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.example.cremallerademontserrat.R

class InsertarDatos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_datos)

        // Llamada a la función para insertar datos en Firebase Realtime Database
        insertarDatosEnFirebase()
    }

    private fun insertarDatosEnFirebase() {
        // Obtener una instancia de FirebaseDatabase
        val database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/")

        // Referencia a la base de datos donde se insertarán los datos
        val reference = database.getReference("NOVEDADES")

        // Datos predefinidos a insertar
        val novedades = listOf(
            hashMapOf(
                "categoria" to "Escolania",
                "titulo" to "Novedad 1 - Escolanía",
                "descripcion" to "Descripción de la novedad 1 para Escolanía de Montserrat",
                "fechaInicio" to "2024-06-01",
                "fechaFinal" to "2024-06-10",
                "imagen" to "https://exterior.cat/wp-content/uploads/escolania-montserrat.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Escolania",
                "titulo" to "Novedad 2 - Escolanía",
                "descripcion" to "Descripción de la novedad 2 para Escolanía de Montserrat",
                "fechaInicio" to "2024-06-05",
                "fechaFinal" to "2024-06-15",
                "imagen" to "https://static1.ara.cat/clip/618a880b-a079-40f6-9c4e-dbef1123e8cc_source-aspect-ratio_default_0.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Escolania",
                "titulo" to "Novedad 3 - Escolanía",
                "descripcion" to "Descripción de la novedad 3 para Escolanía de Montserrat",
                "fechaInicio" to "2024-06-10",
                "fechaFinal" to "2024-06-20",
                "imagen" to "https://www.elnacional.cat/uploads/s1/40/03/14/21/cor-escolania-de-montserrat.jpeg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Santuari",
                "titulo" to "Novedad 1 - Santuari",
                "descripcion" to "Descripción de la novedad 1 para Santuari de Montserrat",
                "fechaInicio" to "2024-06-01",
                "fechaFinal" to "2024-06-10",
                "imagen" to "https://www.barcelo.com/guia-turismo/wp-content/uploads/2019/06/montserrat-monasterio.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Santuari",
                "titulo" to "Novedad 2 - Santuari",
                "descripcion" to "Descripción de la novedad 2 para Santuari de Montserrat",
                "fechaInicio" to "2024-06-05",
                "fechaFinal" to "2024-06-15",
                "imagen" to "https://upload.wikimedia.org/wikipedia/commons/e/ed/Montserrat_Natural_Park_4.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Santuari",
                "titulo" to "Novedad 3 - Santuari",
                "descripcion" to "Descripción de la novedad 3 para Santuari de Montserrat",
                "fechaInicio" to "2024-06-10",
                "fechaFinal" to "2024-06-20",
                "imagen" to "https://smart-lighting.es/wp-content/uploads/2022/03/0-monasterio-montserrat-unsplahs.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Amics",
                "titulo" to "Novedad 1 - Amics",
                "descripcion" to "Descripción de la novedad 1 para Amics de Montserrat",
                "fechaInicio" to "2024-06-01",
                "fechaFinal" to "2024-06-10",
                "imagen" to "https://www.annum-munir.com/hs-fs/hubfs/Images/Adventure/Montserrat/muslim-travel-guide-Montserrat-day-trip-from-Barcelona.jpg?width=4032&name=muslim-travel-guide-Montserrat-day-trip-from-Barcelona.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Amics",
                "titulo" to "Novedad 2 - Amics",
                "descripcion" to "Descripción de la novedad 2 para Amics de Montserrat",
                "fechaInicio" to "2024-06-05",
                "fechaFinal" to "2024-06-15",
                "imagen" to "https://estaticos-cdn.prensaiberica.es/clip/23a1863b-1614-4929-93ef-a846a9bb0f8c_alta-libre-aspect-ratio_default_0.jpg",
                "usuarioId" to ""
            ),
            hashMapOf(
                "categoria" to "Amics",
                "titulo" to "Novedad 3 - Amics",
                "descripcion" to "Descripción de la novedad 3 para Amics de Montserrat",
                "fechaInicio" to "2024-06-10",
                "fechaFinal" to "2024-06-20",
                "imagen" to "https://guias-viajar.com/wp-content/uploads/2017/05/barcelona-monasterio-monserrat-001.jpg",
                "usuarioId" to ""
            )
        )

        // Insertar los datos en la base de datos
        reference.setValue(novedades)
            .addOnSuccessListener {
                Log.d("InsertarDatos", "Datos insertados correctamente en Firebase Realtime Database.")
            }
            .addOnFailureListener { e ->
                Log.e("InsertarDatos", "Error al insertar datos en Firebase Realtime Database", e)
            }
    }
}
