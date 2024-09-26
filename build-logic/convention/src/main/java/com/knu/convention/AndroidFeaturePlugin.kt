package com.knu.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

// Android library 모듈에 대한 플러그인
class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.apply("com.android.library")
        configureAndroidCommonPlugin()
    }
}