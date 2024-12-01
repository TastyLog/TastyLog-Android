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
    implementation(project(":core:navigation"))
    implementation(project(":core:youtube"))
    implementation(project(":feature:search"))
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.android.fragment.navigation)
    implementation(libs.coil)
    implementation(libs.shimmer)
    implementation(libs.custom.rating.bar)
    implementation(libs.youtubeplayer)
    implementation(libs.kakao.share)
    implementation(libs.circle.imageview)
    implementation(libs.shimmer)
    implementation(libs.naver.map.sdk)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
}
