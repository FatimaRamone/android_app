package com.example.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Obtener el texto pasado desde MainActivity
        val detectedText = intent.getStringExtra("DETECTED_TEXT")

        // Encontrar el TextView en el layout y establecer el texto
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = detectedText ?: "No text detected"
    }
}
