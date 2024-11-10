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
    implementation(project(":feature:list"))
    implementation(libs.fragment.ktx)
    implementation(libs.naver.map.sdk)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.circle.imageview)
    implementation(libs.coil)
    implementation(libs.custom.rating.bar)
    implementation(libs.youtubeplayer)
    implementation(libs.kakao.share)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}
