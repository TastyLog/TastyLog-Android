plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.search"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:navigation"))
    implementation(project(":domain:home"))
    implementation(libs.fragment.ktx)
    implementation(project(":core:datastore"))
    implementation(project(":domain:search"))
    implementation(project(":data:search"))
}
