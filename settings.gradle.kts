plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "chesnay-bot"
include("common", "discord-bot", "cli-remote")