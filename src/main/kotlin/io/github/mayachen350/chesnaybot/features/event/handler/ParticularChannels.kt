package io.github.mayachen350.chesnaybot.features.event.handler

import dev.kord.core.event.message.MessageCreateEvent
import io.github.mayachen350.chesnaybot.Configs
import io.github.mayachen350.chesnaybot.features.event.logic.mayaIdeaSpamHandler
import me.jakejmattson.discordkt.dsl.listeners

fun particularChannelsListeners() = listeners {
    on<MessageCreateEvent> {
        if (this.guildId?.value == Configs.serverId && this.message.channel.id.value == Configs.mayaIdeaSpamChannelid /*&& this.member != null*/)
            mayaIdeaSpamHandler(this)
    }
}