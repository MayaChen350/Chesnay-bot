package io.github.mayachen350.chesnaybot.bot

import io.github.mayachen350.chesnaybot.bot.features.event.logic.log
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

object RemoteAccess {
    const val HOSTNAME: String = ""

    lateinit var writeChannel: ByteWriteChannel

    suspend fun connect() {
        coroutineScope {
            try {
                val selectorManager = SelectorManager(Dispatchers.IO)
                val serverSocket = aSocket(selectorManager).tcp().bind("127.0.0.1", 3843)

                val socket = serverSocket.accept()
                writeChannel = socket.openWriteChannel()

                socket.openReadChannel().run {

                    withContext(Dispatchers.IO) {
                        while (this.isActive) {
                            while (this@run.availableForRead != 0) {
                                handle(this@run.readUTF8Line())
                            }
                        }
                    }
                }

                socket.awaitClosed()

            } catch (e: Exception) {
                println(
                    "The socket for remote access to the bot failed." +
                            "\nError: ${e.message}" +
                            "\nCause: ${e.cause})"
                )

                log(getGuild()) {
                    title = "Remote access: Error"
                    description = "An error happened when trying to access the remote server" +
                            "\nError: ${e.message}"
                }
            }
        }
    }

    suspend fun handle(response: String?) {
        if (response != null) {
            writeChannel.writeStringUtf8(
                when (response) {
                    "hi" -> "hi!"
                    else -> "idk what to say to that"
                }
            )
        }
    }
}