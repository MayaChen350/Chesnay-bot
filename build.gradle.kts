plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.flyway) apply false
}

subprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

//    group = "io.github.mayachen350"
}