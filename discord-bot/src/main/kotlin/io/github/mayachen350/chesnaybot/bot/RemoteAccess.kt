package io.github.mayachen350.chesnaybot.bot

import io.github.mayachen350.chesnaybot.bot.features.event.logic.log
import io.github.mayachen350.chesnaybot.common.thanks.VarInt
import io.github.mayachen350.chesnaybot.common.thanks.VarInt.getVarInt
import io.github.mayachen350.chesnaybot.common.thanks.VarInt.putVarInt
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.asStream
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.toOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.io.readByteArray
import kotlinx.io.readString
import java.nio.ByteBuffer

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
                        // TODO: Uhm test this (is this stupid)
                        while (this.isActive) {
                            while (this@run.availableForRead != 0) {
                                val sizeRead: Int = getVarInt(this@run.readBuffer())
                                handle(this@run.readBuffer(sizeRead).readString())
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
            val responseToReponse = when (response) {
                "hi" -> "hi!"
                else -> "idk what to say to that"
            }
            putVarInt(responseToReponse.length, writeChannel.toOutputStream())
            writeChannel.writeStringUtf8(responseToReponse)
        }
    }
}