import java.io.FileInputStream
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.flyway)
}


group = "io.github.mayachen350"
version = Properties().run {
    load(FileInputStream("src/main/resources/bot.properties"))
    getProperty("version")
}
description = "Official bot of the Salon de Chesnay Discord Server."

dependencies {
    implementation(libs.discordkt)
    implementation(libs.dotenv)
    implementation(libs.coroutines.core)
    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.migration)
    implementation(libs.exposed.dao)
    implementation(libs.mysql.connector)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    manifest {
        attributes["Main-Class"] = "io.github.mayachen350.chesnaybot.AppKt"
    }
}

kotlin {
    jvmToolchain(22)
}

flyway {
    Path("${project.projectDir}/src/main/resources/db/migration/").run {
        if (notExists())
            createDirectories()
    }

    url = ":r2dbc://192.168.0.188:3306/SalonChesnay"
    baselineOnMigrate = true
}