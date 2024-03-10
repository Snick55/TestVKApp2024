plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.snick55.testvkapp2024"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.snick55.testvkapp2024"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding{
        enable = true
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    val navVersion = "2.5.3"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")


    implementation ("com.github.bumptech.glide:glide:4.16.0")

}