@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.designsystem"
}
dependencies {
    implementation(project(":domain:home"))
    implementation(project(":core:common"))
}
