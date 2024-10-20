plugins {
    alias(libs.plugins.android.application) apply false // Apply false because it's in the app-level gradle
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    //id("com.google.dagger.hilt.android") version "2.51.1" apply false
    //id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}