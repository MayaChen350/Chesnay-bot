package io.github.mayachen350.chesnaybot.features.system.roleChannel

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent
import dev.kord.rest.request.RestRequestException
import io.github.mayachen350.chesnaybot.backend.ValetService
import io.github.mayachen350.chesnaybot.features.event.logic.dreamhouseEmbedLogDefault
import io.github.mayachen350.chesnaybot.features.event.logic.log
import io.github.mayachen350.chesnaybot.features.utils.ReactionEvent
import io.github.mayachen350.chesnaybot.features.utils.hasRole
import io.github.mayachen350.chesnaybot.features.utils.isInRoleChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.jakejmattson.discordkt.util.toSnowflake
import me.jakejmattson.discordkt.util.trimToID

/** Union of the logic for the role assignment channel.**/
class RoleChannelDispenser(
    addEvent: ReactionAddEvent? = null,
    removeEvent: ReactionRemoveEvent? = null
) {

    companion object {
        /** Find the role from the message reacted and returns its snowflake.
         *
         * Return null if not found.**/
        fun findRoleFromEmoji(messageContent: String, emoji: ReactionEmoji): Snowflake? = with(messageContent) {
            // Search if the name of the emoji appears on the message
            if (indexOf(emoji.mention) != -1) {
                // Cut the message from when it finds the emoji (also removes '<' if it has)
                val firstCut: String = substring(indexOf(emoji.mention) + 1 /* possible '<' char*/)

                // Get the role id from the first or second '<' character found
                firstCut.run {
                    val roleMentionStartIndex = indexOf("<")
                    val nbCharsRoleMention = 23

                    substring(roleMentionStartIndex, roleMentionStartIndex + nbCharsRoleMention)
                        .trimToID()
                        .toSnowflake()
                }
            } else null
        }
    }

    /** Will be an ReactionAddEvent or a ReactionRemoveEvent depending on the context. **/
    private val event: ReactionEvent = ReactionEvent(addEvent, removeEvent)

    /** The actual discord event listener logic. **/
    suspend fun execute() {
        val message: Message? = event.getMessageOrNull()

        if (message?.isInRoleChannel() == true) {
            // Search for the role
            val roleFoundId: Snowflake? = findRoleFromEmoji(message.content, event.emoji)

            if (roleFoundId != null) {
                val member = event.getUserAsMember()

                if (member != null) {
                    try {
                        // If there's not any reaction from the bot for this specific reaction add one
                        if (member.isBot && message.reactions
                                .filter { it.emoji == event.emoji }
                                .none { it.selfReacted }
                        ) {
                            message.addReaction(event.emoji)
                        }

                        if (event.isAddEvent() && !member.hasRole(roleFoundId)) {
                            member.addRole(roleFoundId)
                            ValetService.saveRoleAdded(member.id.value, roleFoundId.value)
                        } else if (event.isRemoveEvent() && member.hasRole(roleFoundId)) {
                            member.removeRole(roleFoundId)
                            ValetService.saveRoleRemoved(member.id.value, roleFoundId.value)
                        }

                        log(event.guild!!, member) {
                            dreamhouseEmbedLogDefault(member)

                            title =
                                if (event.isAddEvent()) "Role added via the role channel"
                                else "Role removed via the role channel"
                            description = "Role: ${event.getRole(roleFoundId).mention}"
                        }
                    } catch (e: RestRequestException) {
                        println("An error happened in the role channel: ${e.error}")
                        log(event.guild!!, member) {
                            dreamhouseEmbedLogDefault(member)

                            title =
                                if (event.isAddEvent()) "Couldn't add role via the role channel"
                                else "Couldn't remove Role via the role channel"
                            description = "An error happened: ${e.error?.message ?: e.message}\n" +
                                    "Role: ${event.getRole(roleFoundId).mention}"
                        }
                    }
                }
            }
        }
    }
}
