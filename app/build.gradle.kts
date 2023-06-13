plugins {
    id("com.android.application")
    kotlin("android")
}
repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
android {
    namespace = "com.example.calories"

    compileSdk = deps.android.build.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.calories"
        minSdk = deps.android.build.minSdkVersion
        targetSdk = deps.android.build.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":dagger"))
    implementation(project(":calories"))

    implementation(deps.android.androidx.corektx)
    // dagger
    implementation(deps.dagger.core)
}