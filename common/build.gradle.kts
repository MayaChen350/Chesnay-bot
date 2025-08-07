plugins {
    kotlin("jvm") version "2.2.0"
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.network)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}