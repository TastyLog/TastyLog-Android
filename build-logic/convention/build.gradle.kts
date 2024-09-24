plugins {
    `kotlin-dsl`
}

group = "com.knu.retastylog.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("android-application") {
            id = "com.knu.retastylog.application"
            implementationClass = "com.knu.convention.AndroidApplicationPlugin"
        }
        create("android-feature") {
            id = "com.knu.retastylog.feature"
            implementationClass = "com.knu.convention.AndroidFeaturePlugin"
        }
        create("android-kotlin") {
            id = "com.knu.retastylog.kotlin"
            implementationClass = "com.knu.convention.AndroidKotlinPlugin"
        }
        create("android-hilt") {
            id = "com.knu.retastylog.hilt"
            implementationClass = "com.knu.convention.AndroidHiltPlugin"
        }
        create("kotlin-serialization") {
            id = "com.knu.retastylog.serialization"
            implementationClass = "com.knu.convention.KotlinSerializationPlugin"
        }
    }
}