package io.github.mayachen350.chesnaybot.features.event.logic

import dev.kord.core.behavior.channel.asChannelOf
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.message.MessageCreateEvent

suspend fun mayaIdeaSpamHandler(event: MessageCreateEvent): Unit = with(event) {
    val threadTitle: String = message.content.run {
        val split = split(" ")
        val size = 5/*words*/

        if (split.size < size) split.take(size).reduce { acc, string -> "$acc $string" }.plus("...") else this
    }

    message.channel.asChannelOf<TextChannel>().startPublicThreadWithMessage(
        messageId = message.id,
        name = "Discussion: $threadTitle",
    )
}