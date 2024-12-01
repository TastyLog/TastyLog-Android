plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.data.search"
}

dependencies {
    implementation(project(":domain:search"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.androidx.hilt.common)
}
