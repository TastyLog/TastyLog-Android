@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.knu.retastylog.application")
}

android {
    namespace = "com.knu.retastylog"

    defaultConfig {
        applicationId = "com.knu.retastylog"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(project(":domain:home"))
    implementation(project(":data:home"))
    implementation(project(":domain:onboarding"))
    implementation(project(":data:onboarding"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:home"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
}
