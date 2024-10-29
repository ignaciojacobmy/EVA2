package com.example.eva2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var nameDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializa las vistas
        nameInput = findViewById(R.id.name_input)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)
        nameDisplay = findViewById(R.id.name_display)

        // Obtener el nombre pasado desde HomeActivity
        val receivedName = intent.getStringExtra("user_name")
        if (!receivedName.isNullOrEmpty()) {
            nameInput.setText(receivedName) // Pre-llenar el campo de entrada con el nombre recibido
            nameDisplay.text = "Nombre Guardado: $receivedName"
        }

        // Botón para actualizar el nombre
        updateButton.setOnClickListener {
            updateName()
        }

        // Botón para eliminar el perfil
        deleteButton.setOnClickListener {
            deleteProfile()
        }
    }

    private fun updateName() {
        val name = nameInput.text.toString().trim()
        if (name.isNotEmpty()) {
            // Guardar el nuevo nombre en SharedPreferences
            getSharedPreferences("user_prefs", MODE_PRIVATE).edit().putString("user_name", name).apply()
            Toast.makeText(this, "Nombre actualizado", Toast.LENGTH_SHORT).show()

            // Retornar el nombre actualizado a HomeActivity
            val resultIntent = Intent()
            resultIntent.putExtra("updated_name", name)
            setResult(RESULT_OK, resultIntent)

            // Cierra ProfileActivity y regresa a HomeActivity
            finish()
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteProfile() {
        // Eliminar el nombre de SharedPreferences
        getSharedPreferences("user_prefs", MODE_PRIVATE).edit().remove("user_name").apply()
        Toast.makeText(this, "Perfil eliminado", Toast.LENGTH_SHORT).show()

        // Cierra ProfileActivity y regresa a HomeActivity
        finish()
    }
}
