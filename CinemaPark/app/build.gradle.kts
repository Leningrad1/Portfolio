plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.example.cinemapark"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cinemapark"
        minSdk = 24
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

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.1.0")) //Firebase
    implementation("com.google.firebase:firebase-analytics") //FB analitics
    implementation("com.google.firebase:firebase-crashlytics") //FB Crashlytics
    implementation ("me.relex:circleindicator:2.1.6") //CircleIndicator
    implementation("androidx.room:room-runtime:2.6.1") // Библиотека "Room"
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // Дополнительно для Kotlin Coroutines, Kotlin Flows
    implementation("androidx.room:room-rxjava2:2.6.1") // optonal-rxjava support for Room
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    val paging_version = "3.2.1"
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    /*    val nav_version = "2.7.5"
        implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")*/
    //recycle
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    //ViewModel + activity
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
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
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}