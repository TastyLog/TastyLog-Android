@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.knu.retastylog.feature")
}

android {
    namespace = "com.knu.retastylog.datastore"
}

dependencies {
    implementation(project(":core:common"))
}
