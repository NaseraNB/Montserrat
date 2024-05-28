// Paquets
package com.example.cremallerademontserrat.models.models

// Importacions
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.cremallerademontserrat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError

class Menu : DialogFragment() {

    // Variables
    private lateinit var menuTanca: TextView
    private lateinit var menuFlecha: ImageView
    private lateinit var menuRutes: TextView
    private lateinit var menuInici: TextView
    private lateinit var menuTransport: TextView
    private lateinit var menuNom: TextView // El TextView para el nombre del usuario
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Mostra l'activitat del menú
        val vista = inflater.inflate(R.layout.activity_menu, container, false)
        return vista // Mostra la vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar Firebase Auth y Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://montserrat-express-default-rtdb.europe-west1.firebasedatabase.app/").getReference("USUARIS")

        // Buscar les ID dels components.
        menuTanca = view.findViewById(R.id.menuTancarSessio)
        menuFlecha = view.findViewById(R.id.menuFlecha)
        menuRutes = view.findViewById(R.id.menuRutes)
        menuInici = view.findViewById(R.id.menuInici)
        menuTransport = view.findViewById(R.id.menuTransport)
        menuNom = view.findViewById(R.id.menuNom) // El TextView para el nombre del usuario

        // Obtener el nombre del usuario actual
        obtenerNombreUsuario()

        // Clic de cada component.
        menuInici.setOnClickListener {
            obrirInici() // Mètode
        }

        menuTransport.setOnClickListener(){
            obrirTransport() // Mètode
        }

        menuFlecha.setOnClickListener(){
            mostrarOpcions() // Mètode
        }

        menuRutes.setOnClickListener(){
            obrirRutes() // Mètode
        }

        menuTanca.setOnClickListener(){
            obrirTacarSessio() // Mètode
        }
    }

    // Obtener el nombre del usuario desde Firebase Realtime Database
    private fun obtenerNombreUsuario() {
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null) {
            database.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val nombre = dataSnapshot.child("nom").getValue(String::class.java)
                    menuNom.text = nombre ?: ""
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    menuNom.text = ""
                }
            })
        } else {
            menuNom.text = ""
        }
    }

    // Mostrar el menú cap a la dreta
    override fun onResume() {
        super.onResume()

        // Ample del menú
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setGravity(Gravity.TOP or Gravity.END)
    }

    // Mètodes que obre una activitat
    private fun obrirInici() {
        val intent = Intent(requireContext(), Inici::class.java)
        startActivity(intent)
        dismiss()
    }

    private fun obrirTransport() {
        val intent = Intent(requireContext(), Transport::class.java)
        startActivity(intent)
        dismiss()
    }

    private fun obrirRutes() {
        val intent = Intent(requireContext(), Rutes::class.java)
        startActivity(intent)
        dismiss()
    }

    private fun obrirTacarSessio() {
        auth.signOut() // Cerrar sesión
        val intent = Intent(requireContext(), Inici_De_Sessio::class.java) // Redirigir a la pantalla de inicio de sesión
        startActivity(intent)
        dismiss()
    }

    // Mètode que mostra les opcions i inicia una activitat.
    private fun mostrarOpcions() {
        val menu = PopupMenu(requireContext(), menuFlecha)
        menu.menuInflater.inflate(R.menu.menu_array, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.tren -> {
                    val intent = Intent(requireContext(), Cremallera::class.java)
                    startActivity(intent)
                    dismiss()
                    true
                }
                R.id.aeri -> {
                    val intent = Intent(requireContext(), Aeri::class.java)
                    startActivity(intent)
                    dismiss()
                    true
                }
                R.id.funicularSantJoan -> {
                    val intent = Intent(requireContext(), FunicularSantJoan::class.java)
                    startActivity(intent)
                    dismiss()
                    true
                }
                R.id.funicularSantaCova -> {
                    val intent = Intent(requireContext(), FunicularSantaCova::class.java)
                    startActivity(intent)
                    dismiss()
                    true
                }
                else -> false
            }
        }
        menu.show()
    }
}