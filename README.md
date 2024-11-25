# Explore App

## 📖 Descripción
Explore es una aplicación Android que permite capturar imágenes desde la cámara del dispositivo y procesarlas utilizando **ML Kit** para reconocimiento de texto. Los textos extraídos se pasan a una segunda actividad para su visualización.

## 🚀 Características
- Captura de imágenes con la cámara.
- Procesamiento de imágenes para el reconocimiento de texto utilizando **ML Kit**.
- Persistencia de datos en una base de datos local utilizando **Room**.
- Navegación entre actividades para mostrar los resultados del texto detectado.

## 🛠️ Tecnologías Usadas
- **Android SDK** para desarrollo nativo.
- **ML Kit** de Google para reconocimiento de texto.
- **Room Database** para gestión de datos locales.
- **Kotlin Coroutines** para operaciones asíncronas.
- **Lifecycle** para observar y reaccionar a los cambios de datos.

## 📋 Requisitos
- Android Studio instalado (versión recomendada: Arctic Fox o superior).
- SDK de Android configurado (versión mínima: API 21).
- Dispositivo o emulador con cámara.

## 🏗️ Estructura del Proyecto
### Paquetes principales
- **`com.example.explore`**  
  Contiene las actividades principales y la lógica del flujo de la app.

### Actividades
- **`MainActivity`**  
  - Captura imágenes desde la cámara.
  - Procesa el texto utilizando ML Kit.
  - Navega a `SecondActivity` con los resultados del texto detectado.

- **`SecondActivity`**  
  - Muestra el texto detectado recibido desde `MainActivity`.

## 🚀 Configuración Inicial
1. **Clonar el repositorio**
   ```bash
   git clone <REPO_URL>
   cd ExploreApp

    Abrir en Android Studio
    Abre el proyecto desde Android Studio y permite que el IDE sincronice las dependencias.

    Configurar permisos en el archivo AndroidManifest.xml Asegúrate de que los siguientes permisos estén presentes:

<uses-permission android:name="android.permission.CAMERA" />

Dependencias requeridas
Verifica que las siguientes bibliotecas estén configuradas en el archivo build.gradle:

    implementation "com.google.android.gms:play-services-mlkit-text-recognition:16.1.3"
    implementation "androidx.room:room-runtime:2.5.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.0"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.6.0"

    Sincronizar y construir el proyecto
    En Android Studio, selecciona Sync Project with Gradle Files.

🖱️ Uso de la Aplicación

    Abrir la aplicación: Al iniciarla, verás un botón para abrir la cámara.
    Capturar una imagen: Pulsa el botón, otorga los permisos necesarios y captura una imagen.
    Procesar la imagen: El texto detectado será procesado y enviado a la siguiente actividad.
    Ver resultados: Observa el texto extraído en la SecondActivity.

🔧 Detalles Técnicos
Manejo de Permisos

    Verifica y solicita el permiso para la cámara antes de abrirla:

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1001)
        } else {
            openCamera()
        }
    }

Reconocimiento de Texto con ML Kit

    Procesa imágenes con el cliente de texto de ML Kit:

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            val resultText = visionText.text
            Log.d("MainActivity", "Detected Text: $resultText")
        }
        .addOnFailureListener { e ->
            e.printStackTrace()
        }

Navegación entre Actividades

    Pasa los datos detectados a SecondActivity mediante un Intent:

    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra("DETECTED_TEXT", resultText)
    startActivity(intent)

🛠️ Mejoras Futuras

    Añadir un historial de imágenes procesadas.
    Mejorar el diseño de la interfaz con Material Design.
    Implementar una funcionalidad offline para la base de datos.

📝 Licencia

Este proyecto se distribuye bajo la Licencia MIT.

