// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false  // Обнови до 1.9.20
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false  // KSP для 1.9.20  // Добавь KSP здесь
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("androidx.room") version "2.6.0" apply false
}