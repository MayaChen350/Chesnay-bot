import java.io.FileInputStream
import java.util.Properties
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.flyway)
}


version = Properties().run {
    load(FileInputStream("discord-bot/src/main/resources/bot.properties"))
    getProperty("version")
}
description = "Official bot of the Salon de Chesnay Discord Server."

dependencies {
    implementation(libs.discordkt)
    implementation(libs.dotenv)
    implementation(libs.coroutines.core)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.migration)
    implementation(libs.exposed.dao)
    implementation(libs.mysql.connector) // it's vulnerable :c
    implementation(libs.ktor.network)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    manifest {
        attributes["Main-Class"] = "io.github.mayachen350.chesnaybot.bot.AppKt"
    }
}

flyway {
    Path("${project.projectDir}/src/main/resources/db/migration/").run {
        if (notExists())
            createDirectories()
    }

    url = ":jdbc://192.168.0.188:3306/SalonChesnay"
    baselineOnMigrate = true
}

kotlin {
    jvmToolchain(22)
}