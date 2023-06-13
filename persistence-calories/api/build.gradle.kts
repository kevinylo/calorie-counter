plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = deps.android.build.compileSdkVersion

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(deps.kotlin.stdlib.jdk7)
    implementation(deps.android.androidx.roomKtx)

    api(project(":model-persistence"))
    api(deps.rx.rx)
    implementation(deps.util.joda)
}
