@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.bundles.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging.interceptor)
}
