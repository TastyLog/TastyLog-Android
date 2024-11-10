plugins {
    id("com.knu.retastylog.feature")
    id("com.knu.retastylog.hilt")
}

android {
    namespace = "com.knu.retastylog.data.list"
}

dependencies {
    implementation(project(":domain:list"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(libs.bundles.retrofit)
}
