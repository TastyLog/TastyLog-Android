plugins {
    id("com.knu.retastylog.feature")
    id("com.knu.retastylog.hilt")
}

android {
    namespace = "com.knu.retastylog.data.home"
}

dependencies {
    implementation(project(":domain:home"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(libs.bundles.retrofit)
}
