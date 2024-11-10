plugins{
    id("com.knu.retastylog.feature")
}

android{
    namespace = "com.knu.retastylog.list"
}

dependencies{
    implementation(project(":domain:home"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(libs.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.android.fragment.navigation)
    implementation(libs.coil)
    implementation(libs.shimmer)
    implementation(libs.custom.rating.bar)
    implementation(libs.youtubeplayer)
    implementation(libs.circle.imageview)
    implementation(libs.shimmer)
}
