plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
}

version = "0.1.0"
description = "Remote access to the bot's stuff or something with sockets"

dependencies {
    implementation(libs.netty.client)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    manifest {
        attributes["Main-Class"] = "io.github.mayachen350.chesnaybot.remote.AppKt"
    }
}


kotlin {
    jvmToolchain(22)
}