plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.headhunterapps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.headhunterapps"
        minSdk = 34
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {
// Библиотека "Room"
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // Дополнительно для Kotlin Coroutines, Kotlin Flows
    implementation("androidx.room:room-rxjava2:2.6.1") // optonal-rxjava support for Room

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    //ViewModel + activity
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //moshi
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    //gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //logginInterceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    //Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.9")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.9")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}