plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "chesnay-bot"
include(":discord-bot")
include(":cli-remote")