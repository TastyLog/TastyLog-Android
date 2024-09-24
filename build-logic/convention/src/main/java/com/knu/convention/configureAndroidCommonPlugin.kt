package com.knu.convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

// 공통 적용 설정 정의
internal fun Project.configureAndroidCommonPlugin() {
    val properties = Properties().apply {
        val file = rootProject.file("local.properties")
        if (file.exists()) {
            load(file.inputStream())
        } else {
            // local.properties 파일이 없을 경우 처리
        }
    }

    // Kotlin, Hilt 플러그인
    apply<AndroidKotlinPlugin>()
    apply<KotlinSerializationPlugin>()
    apply<AndroidHiltPlugin>()

    // 공통적인 Android 설정 적용
    extensions.getByType<BaseExtension>().apply {
        defaultConfig {
            // buildConfigField("String", "TASTYLOG_BASE_URL", properties["BASE_URL"] as String)
        }
        buildFeatures.apply {
            viewBinding = true
            buildConfig = true
        }
    }

    // 공통 의존성 추가
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "implementation"(libs.findLibrary("core.ktx").get())
        "implementation"(libs.findLibrary("appcompat").get())
        "implementation"(libs.findLibrary("lifecycle.viewmodel").get())
        "implementation"(libs.findLibrary("material").get())
    }
}