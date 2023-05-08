import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.8.10"
}
val keystorePropertiesFile = rootProject.file("keystore.properties")

android {
    namespace = "com.zerocoders.moviestack"
    compileSdk = 33

    if (keystorePropertiesFile.exists()) {
        val keystoreProperties = Properties()
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        signingConfigs {
            getByName("debug") {
                keyAlias = keystoreProperties["keyAlias"].toString()
                keyPassword = keystoreProperties["keyPassword"].toString()
                storeFile = file(keystoreProperties["storeFile"]!!)
                storePassword = keystoreProperties["storePassword"].toString()
            }
        }
    }

    defaultConfig {
        applicationId = "com.zerocoders.moviestack"
        minSdk = 21
        targetSdk = 33
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (keystorePropertiesFile.exists())
                signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            if (keystorePropertiesFile.exists())
                signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    // Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha09")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose Navigation
    val navVersion = "2.5.3"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    // Exo Player
    implementation("com.google.android.exoplayer:exoplayer:2.18.6")
    implementation("com.google.android.exoplayer:extension-mediasession:2.18.6")

    //Coil
    implementation("io.coil-kt:coil-compose:2.3.0")

    //Paging 3
    implementation("androidx.paging:paging-compose:1.0.0-alpha19")

    // Ktor Client
    implementation("io.ktor:ktor-client-core:2.2.3")
    implementation("io.ktor:ktor-client-okhttp:2.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.3")
    implementation("io.ktor:ktor-client-logging:2.2.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.ktor:ktor-client-mock:2.2.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    // new pipe extractor
    implementation("com.github.TeamNewPipe.NewPipeExtractor:NewPipeExtractor:v0.22.5")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
}