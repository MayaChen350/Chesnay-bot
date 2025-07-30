# Changelog for `Chesnay-Bot`

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### In progress

- System that automatically creates a discussion thread when Maya adds send something in the Maya-idea-spam channel
- Tests for the role channel system

UNTESTED)

### Changed

- Slightly faster bot

More technical stuff:

- Updated dependencies
- Better structure (hopefully)
- Configs are now compile time constants
- Refactor


## 0.3.1 - 2025-07-24

### Added

- The bot now update the members roles from the reactions in the role channel when it restarts
- A lot of delay on other features
- Actual useful readme file

More technical stuff:

- Database (Exposed mysql) (see backend)
- Dockerfile
- Startup logic

## Removed

- Logging edited messages

### Changed

- Faster bot

More technical stuff:

- Refactoring
- Better structure (hopefully)
- Better use of coroutines
- Constants are basically C macros instead of reading them
- Updated dependencies

## 0.3.0 - 2025-02-13

### Added

Bot stuff:

- Logs:
  - (Some kind of) Copy of the audit logs
  - When using moderation commands
  - When the role channel is used

More technical stuff:

- Run configs for IntelliJ IDEA
- Properties files in the resources folder for the strings used by the bot

### Changed

More technical stuff:
- The bot version is now located on resources/bot.properties **only**

## 0.2.0 - 2025-01-07

### Added

Bot stuff:

- Moderation commands:
    - ban: Takes a user and a reason as parameters. The ban is **permanent**.
    - kick: Takes a user and a reason as parameters.
    - mute: Takes a user, an amount of time (the bot supports a lot of time units) and a reason as parameters. The mute
      is a discord timeout.
    - purge: Delete messages. Takes a number of messages in parameters.
- A working role assignment by message reactions system which is depending on what roles comes after an emoji in a
  message
  in the role channel
- Bot configurations

More technical stuff:

- A way to run the bot from a shadow jar
- An error handling utility wrapper function

### Removed

- Message commands

The explanations for this are quite long and I who's writing in this changelog right now, Maya, have had a hard time
with the libraries I am using ([DiscordKt](https://github.com/DiscordKt/DiscordKt) and therefore
also [Kord](https://github.com/kordlib/kord)). The way it handles message commands was simply not something I like or
was able to deal with really so I decided to simply just not include them. Are they just not a *pain* to remember
anyway?

**I will then focus on** creating complete **slash commands** if possible.

## 0.1.0 - 2024-12-15

### Added

- Creation of the project with the library DiscordKt/DiscordKt from GitHub
- Added test features:
    - A "hello world command"
    - A sketch of a choose-your-roles with reactions on a message system
