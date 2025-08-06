package io.github.mayachen350.chesnaybot.bot

import com.corundumstudio.socketio.Configuration
import io.github.mayachen350.chesnaybot.bot.features.event.logic.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.ServerSocket

object RemoteAccess {
    const val HOSTNAME: String = ""

    suspend fun connect() {
        coroutineScope() {
            try {
                val server = ServerSocket(3843)

                val connection = withContext(Dispatchers.Default) {
                    server.accept()!!
                }

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

    suspend fun listen() {

    }


}