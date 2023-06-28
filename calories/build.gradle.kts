plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

android {
    compileSdk = deps.android.build.compileSdkVersion

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        minSdk = deps.android.build.minSdkVersion
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":model-persistence"))
    implementation(project(":model-constant"))
    implementation(project(":core-interface"))
    implementation(project(":dagger"))
    implementation(project(":core-basemrp"))

    implementation(deps.ui.material)
    implementation(deps.ui.composeUi)
    implementation(deps.ui.composeFoundation)
    implementation(deps.ui.composeMaterial3)
    implementation(deps.ui.composeConstraintLayout)

    implementation(deps.util.joda)

    implementation(deps.rx.autodispose)
    implementation(deps.rx.autodisposeLifecycle)
    implementation(deps.rx.autodisposeAndroidKotlin)
    implementation(deps.rx.autodisposeKotlin)
    implementation(deps.rx.autodisposeLifecycleKotlin)
    implementation(deps.rx.autodisposeAndroid)
    implementation(deps.rx.rxBinding)
    implementation(deps.rx.rxBindingCore)

    implementation(deps.android.androidx.appcompat)
    implementation(deps.android.androidx.corektx)
    implementation(deps.android.androidx.cardView)
    implementation(deps.android.androidx.swipeRefresh)
    implementation(deps.android.androidx.constraintLayout)
    implementation(deps.ui.materialDateTime)
    implementation(deps.ui.materialProgressBar)
    implementation(deps.ui.mpAndroidChart)
    implementation(deps.reporting.timber)

    // dagger
    implementation(deps.dagger.core)
    implementation(deps.android.androidx.corektx)
    kapt(deps.kapt.daggerCompiler)

    implementation(deps.android.androidx.recyclerView)

    testImplementation(deps.test.junit5)
    testImplementation(deps.test.assertj)
    testImplementation(deps.test.mockK)
}