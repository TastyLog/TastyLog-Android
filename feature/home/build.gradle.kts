plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.home"
}

dependencies {
    implementation(project(":domain:home"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(libs.fragment.ktx)
}
