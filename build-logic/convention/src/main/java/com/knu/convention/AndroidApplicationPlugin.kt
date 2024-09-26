package com.knu.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

// Android application 모듈에 대한 플러그인
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.apply("com.android.application")
        configureAndroidCommonPlugin()
        logger.lifecycle("AndroidApplicationPlugin applied")
    }
}
