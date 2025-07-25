# Chesnay-bot *(Le Valet De Chesnay)*

![Static Badge](https://img.shields.io/badge/v2.0.20-Kotlin?logo=Kotlin&label=Kotlin&color=%23A97BFF&link=https%3A%2F%2Fkotlinlang.org%2F)
![Static Badge](https://img.shields.io/badge/v0.3.1-version?style=flat&label=Version)
![Static Badge](https://img.shields.io/badge/v0.13.1-kord?style=flat&label=Kord&color=orange)

Official bot of the *Salon De Chesnay* Discord server, AKA my server!  
It's written most entirely in [Kotlin](https://kotlinlang.org/), using the [DiscordKt](https://github.com/DiscordKt/DiscordKt) framework.

There's not so much reasons for me to have done the bot except for fun! (fun, kotlin, get it? get it?)  
Though my server being closed for me and my friends why do I even make this bot open-source you ask? Well for everyone to check it out obviously!   
Feel free to try [building and running](#building-and-running) the bot too!  
(If you're a friend of mine I'll gladly make you join the server too, just ask in DM. A lot of yapping there though)

## Notes
Chesnay-Bot has been marked as being currently in "BETA", as it might not be entirely stable.

## Structure

### Entry point
The bot entry point is [App.kt](https://github.com/MayaChen350/Chesnay-bot/blob/main/src/main/kotlin/io/github/mayachen350/dreamhousebot/App.kt)  
It contains:
- Configurations
- Registering of **commands**/**listeners** groups
- A command I use for testing stuff

---

### Features
[Every features are under the same folder.](https://github.com/MayaChen350/Chesnay-bot/tree/main/src/main/kotlin/io/github/mayachen350/dreamhousebot/features)

Almost all of them are separated in two folders: `logic` and `handler`
- The `handler` files define the actual **commands** and **listeners** groups. As they're pretty lightweight, you can see them as **contracts**. Things defined there exists for the bot.
- The `logic` files define all the **actual command and listener logic**. Those can also use some of the `Utils` of the project as I try to minimize the duplication of code.

Commands are also automatically described in the [commands.md](https://github.com/MayaChen350/Chesnay-bot/blob/v0.3.1/commands.md) file.

---

## Building and running
You can simply just run `gradlew build` to build.  
But to run the bot you should probably run `gradlew shadowJar` then try `run.bat` or `run.sh`

If you are having a hard time with anything feel free to just build a docker image and run it, as there's a Dockerfile here for that, and it should work well. 

## Trivia
- Chesnay Bot was supposed to be made in Haskell at start as a kind of challenge, but after having a hard time with it, I decided to use Kotlin for it instead. (Kotlin is my favorite programming language!)
