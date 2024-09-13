package com.example.explore

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.firstOrNull

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar vistas
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        saveButton = findViewById(R.id.saveButton)

        // Configurar el listener del botón
        saveButton.setOnClickListener {
            Log.d("RegisterActivity", "Button clicked") // Agrega este log
            saveUser()
        }
    }

    private fun saveUser() {
        Log.d("RegisterActivity", "saveUser() called") // Seguimiento de la llamada de función
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()

        if (username.isNotEmpty() && email.isNotEmpty()) {
            Log.d("RegisterActivity", "Both fields are filled: username=$username, email=$email")

            lifecycleScope.launch {
                try {
                    val emailExists = userViewModel.getUserByEmail(email).firstOrNull() != null
                    val usernameExists = userViewModel.getUserByUsername(username).firstOrNull() != null

                    Log.d("RegisterActivity", "Email exists: $emailExists, Username exists: $usernameExists")

                    if (emailExists) {
                        Toast.makeText(this@RegisterActivity, "Email already in use", Toast.LENGTH_SHORT).show()
                    } else if (usernameExists) {
                        Toast.makeText(this@RegisterActivity, "Username already in use", Toast.LENGTH_SHORT).show()
                    } else {
                        try {
                            val user = User(username = username, email = email)
                            userViewModel.insertUser(user)
                            Toast.makeText(this@RegisterActivity, "User registered successfully", Toast.LENGTH_SHORT).show()
                            Log.d("RegisterActivity", "User registered successfully")
                            finish()
                        } catch (e: Exception) {
                            Log.e("RegisterActivity", "Error inserting user", e)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RegisterActivity", "Error checking user existence", e)
                }
            }
        } else {
            Log.w("RegisterActivity", "Some fields are empty")
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
