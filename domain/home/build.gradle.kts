plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlin.coroutines.core)
}
