// build.gradle.kts (Project-Level)

plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin.v2020)
        classpath(libs.com.google.devtools.ksp.gradle.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}



