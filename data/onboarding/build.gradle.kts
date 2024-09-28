plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.data.onboarding"
}

dependencies {
    implementation(project(":domain:onboarding"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
}
