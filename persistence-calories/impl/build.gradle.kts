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
    api(project(":persistence-calories:api"))
    api(project(":model-persistence"))
    api(project(":model-constant"))

    implementation(deps.util.joda)
    implementation(deps.kotlin.stdlib.jdk7)

    api(deps.rx.rx)

    implementation(deps.android.androidx.roomRuntime)
    implementation(deps.android.androidx.roomKtx)
    implementation(deps.android.androidx.roomRxJava2)

    kapt(deps.android.androidx.kapt.roomCompiler)
}
