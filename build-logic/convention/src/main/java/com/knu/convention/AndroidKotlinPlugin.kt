package com.knu.convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

// Android Kotlin 플러그인
class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        plugins.apply("kotlin-android")

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        // SDK & Kotlin 설정
        extensions.getByType<BaseExtension>().apply {
            setCompileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

            defaultConfig {
                minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
            }

            compileOptions {
                isCoreLibraryDesugaringEnabled = true
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            (this as ExtensionAware).configure<KotlinJvmOptions> {
                jvmTarget = "17"
            }
        }

        dependencies {
            "coreLibraryDesugaring"(libs.findLibrary("desugarLibs").get())
            "implementation"(libs.findLibrary("kotlin").get())
            "implementation"(libs.findLibrary("kotlin.coroutines").get())
            "implementation"(libs.findLibrary("kotlin.datetime").get())
        }
    }
}