plugins {
  id("com.android.library")
  kotlin("android")
}

repositories {
  google()
  mavenCentral()
}

android {
  compileSdk = deps.android.build.compileSdkVersion

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {

  implementation(project(":core-interface"))
  implementation(project(":model"))
  implementation(project(":model-persistence"))

  implementation(deps.kotlin.stdlib.jdk7)

  implementation(deps.rx.autodispose)

  implementation(deps.rx.rx)
  implementation(deps.rx.rxKotlin)
  implementation(deps.rx.rxRelay)
  implementation(deps.util.joda)
  implementation(deps.reporting.timber)
  implementation(deps.android.androidx.corektx)

    testImplementation(deps.test.junit5)
  testImplementation(deps.test.assertj)
  testImplementation(deps.test.mockK)
}
