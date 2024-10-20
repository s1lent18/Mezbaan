plugins {
    alias(libs.plugins.android.application) // Ensure you are using correct alias for Android plugin
    id("org.jetbrains.kotlin.android") // Kotlin Android Plugin
    //id("com.google.devtools.ksp") version "1.9.0-1.0.12" // KSP Plugin
    //id("com.google.dagger.hilt.android") version "2.51.1" // Hilt Plugin
    alias(libs.plugins.google.gms.google.services) // Google Services Plugin
}

android {
    namespace = "com.example.mezbaan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mezbaan"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.retrofit)
    implementation(libs.androidx.ui)
    //implementation(libs.hilt.android)
    implementation(libs.coil.compose)
    implementation(libs.firebase.auth)
    implementation (libs.converter.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.material3)
    //implementation(libs.androidx.hilt.work)
    implementation(libs.facebook.android.sdk)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.animated.navigation.bar)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.androidx.constraintlayout.compose)
    //implementation(libs.androidx.hilt.navigation.fragment)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material3.window.size.android)

    //ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    //testImplementation(libs.hilt.android.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    //androidTestImplementation(libs.hilt.android.testing)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //kspAndroidTest(libs.hilt.compiler)
    //kspTest(libs.hilt.compiler)
}