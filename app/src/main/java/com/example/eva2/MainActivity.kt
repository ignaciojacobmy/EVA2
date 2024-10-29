package com.example.eva2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.content.Intent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)

        loginBtn.setOnClickListener {
            try {
                val username = usernameInput.text.toString()
                val password = passwordInput.text.toString()

                if (username.isBlank() || password.isBlank()) {
                    Toast.makeText(this, "Por favor, completa ambos campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                val isRegistered = sharedPref.contains("username")

                if (!isRegistered) {
                    registerUser(username, password)
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    if (validateUser(username, password)) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("LOGIN_ERROR", "Error durante el proceso de inicio de sesión: ${e.message}")
                Toast.makeText(this, "Error al procesar el inicio de sesión. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        try {
            val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()
            Log.i("REGISTER", "Usuario registrado correctamente")
        } catch (e: Exception) {
            Log.e("REGISTER_ERROR", "Error al registrar usuario: ${e.message}")
            Toast.makeText(this, "Error al registrar usuario. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateUser(username: String, password: String): Boolean {
        return try {
            val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val savedUsername = sharedPref.getString("username", null)
            val savedPassword = sharedPref.getString("password", null)
            username == savedUsername && password == savedPassword
        } catch (e: Exception) {
            Log.e("LOGIN_ERROR", "Error al validar usuario: ${e.message}")
            Toast.makeText(this, "Error al iniciar sesión. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            false
        }
    }
}