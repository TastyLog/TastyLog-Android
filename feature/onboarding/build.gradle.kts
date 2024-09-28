plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.onboarding"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":feature:home"))
    implementation(project(":domain:onboarding"))
    implementation(libs.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.android.fragment.navigation)
}
