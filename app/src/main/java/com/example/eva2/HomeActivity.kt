package com.example.eva2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        usernameInput = findViewById(R.id.username_input)
        saveButton = findViewById(R.id.save_button)

        // Cargar el nombre guardado al iniciar
        loadSavedName()

        saveButton.setOnClickListener {
            saveName()
        }
    }

    private fun saveName() {
        val name = usernameInput.text.toString().trim()
        if (name.isNotEmpty()) {
            // Guardar el nombre en SharedPreferences
            getSharedPreferences("user_prefs", MODE_PRIVATE).edit().putString("user_name", name).apply()

            // Abrir ProfileActivity
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("user_name", name) // Pasar el nombre a ProfileActivity
            startActivityForResult(intent, 1) // Iniciar ProfileActivity
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
        }
    }

    // Cargar el nombre guardado
    private fun loadSavedName() {
        val savedName = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("user_name", "")
        usernameInput.setText(savedName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val updatedName = data?.getStringExtra("updated_name")
            if (updatedName != null) {
                usernameInput.setText(updatedName) // Actualiza el campo de entrada con el nuevo nombre
                Toast.makeText(this, "Nombre actualizado desde el perfil", Toast.LENGTH_SHORT).show()
            } else {
                usernameInput.text.clear() // Limpia el campo si el perfil fue eliminado
                Toast.makeText(this, "Perfil eliminado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
