pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven{
            url = uri("https://repository.map.naver.com/archive/maven")
        }
        maven{
            url = uri("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "ReTastyLog"
include(":app")
include(":core:common")
include(":core:designsystem")
include(":core:network")
include(":data")
include(":data:home")
include(":domain")
include(":domain:home")
include(":feature:home")
include(":feature:onboarding")
include(":core:datastore")
include(":domain:onboarding")
include(":data:onboarding")
include(":feature:list")
include(":domain:list")
include(":data:list")
