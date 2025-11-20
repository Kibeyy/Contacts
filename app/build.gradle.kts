plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Apply the Hilt Android plugin
    id("com.google.dagger.hilt.android")
    // Apply the KSP plugin
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.addcontacts"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.addcontacts"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Core Voyager navigation
    implementation(libs.voyager.navigator)
    // Optional: Hilt integration
    implementation(libs.voyager.hilt)
    // Optional: Tab navigation
    implementation(libs.voyager.tab.navigator)
    //viewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Hilt main library (used by application/activity classes)
    implementation(libs.dagger.hilt.android)

    // Hilt annotation processor (use KSP)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    // For Hilt's ViewModel support
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.voyager.navigator)

}