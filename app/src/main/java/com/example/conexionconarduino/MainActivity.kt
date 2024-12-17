package com.example.conexionconarduino

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    // Referencia a Firebase
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    // Referencia al TextView donde se mostrará el valor
    private lateinit var dataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar la base de datos de Firebase
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("datos/dato/data")

        // Inicializar el TextView
        dataTextView = findViewById(R.id.dataTextView)

        // Leer el valor desde Firebase en tiempo real
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Leer el valor como String
                val value = dataSnapshot.getValue(String::class.java)

                // Verificar si el valor no es nulo
                if (value != null) {
                    // Mostrar el valor en el TextView
                    dataTextView.text = value
                } else {
                    // Mostrar mensaje en caso de que el valor sea nulo
                    dataTextView.text = "No hay conexión"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores en caso de que falle la lectura de datos
                dataTextView.text = "Error: ${error.message}"
            }
        })
    }
}
