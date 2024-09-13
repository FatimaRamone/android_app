package com.example.explore

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    // Define the ActivityResultLauncher
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>

    // Repository for database operations
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the repository
        userRepository = UserRepository(this)

        // Insert a user and fetch all users
        lifecycleScope.launch {
            val newUser = User(username = "John Doe", email = "fatima@fatimail.com")
            userRepository.insertUser(newUser)
        }

        // Observe changes in the database
        userRepository.getAllUsers().observe(this, Observer { users ->
            users.forEach { user ->
                Log.d("MainActivity", "User: ${user.username}, Age: ${user.email}")
            }
        })

        // Initialize the ActivityResultLauncher
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                if (imageBitmap != null) {
                    processImage(imageBitmap)
                } else {
                    // Handle case where no image was returned
                    Toast.makeText(this, "No image data received", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Find the button by its ID
        val openCameraButton = findViewById<Button>(R.id.openCameraButton)

        // Configure the button click
        openCameraButton.setOnClickListener {
            // Check the camera permission
            checkCameraPermission()
        }
    }

    // Check camera permission
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1001)
        } else {
            // Open the camera if permission is granted
            openCamera()
        }
    }

    // Open the camera
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureLauncher.launch(takePictureIntent)
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Open the camera if permission is granted
                openCamera()
            } else {
                // Show a message if permission is denied
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Process the image with ML Kit
    private fun processImage(imageBitmap: Bitmap) {
        val image = InputImage.fromBitmap(imageBitmap, 0)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val resultText = visionText.text
                Log.d("MainActivity", "Passing text to SecondActivity: $resultText")

                val textToPass = if (resultText.isNotEmpty()) resultText else "No text detected"

                // Start SecondActivity and pass the detected text
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DETECTED_TEXT", textToPass)
                startActivity(intent)

                // Finish MainActivity to ensure it doesn't return to it
                finish()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                // In case of failure, you can also send the user to SecondActivity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DETECTED_TEXT", "Error al detectar texto")
                startActivity(intent)

                // Finish MainActivity to ensure it doesn't return to it
                finish()
            }
    }

}
