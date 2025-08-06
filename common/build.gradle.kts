plugins {
    kotlin("jvm") version "2.2.0"
}

group = "io.github.mayachen350"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}