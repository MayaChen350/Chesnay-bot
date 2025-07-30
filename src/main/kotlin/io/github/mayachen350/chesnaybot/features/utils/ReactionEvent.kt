package io.github.mayachen350.chesnaybot.features.utils

import dev.kord.common.entity.Snowflake
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent

/** Abstraction class making using both type of events easier by sharing common properties and methods. **/
class ReactionEvent(
     val addEvent: ReactionAddEvent? = null,
     val removeEvent: ReactionRemoveEvent? = null
) {

    init {
        if (addEvent == null && removeEvent == null) throw IllegalArgumentException("One of the events must be not null.")
    }

    val emoji = addEvent?.emoji ?: removeEvent!!.emoji
    val guild = addEvent?.guild ?: removeEvent!!.guild
    suspend inline fun getMessage() = addEvent?.getMessage() ?: removeEvent!!.getMessage()
    suspend inline fun getMessageOrNull() = addEvent?.getMessageOrNull() ?: removeEvent!!.getMessageOrNull()
    suspend inline fun getUserAsMember() = addEvent?.getUserAsMember() ?: removeEvent!!.getUserAsMember()
    suspend inline fun getUser() = addEvent?.getUser() ?: removeEvent!!.getUser()
    suspend inline fun getRole(id: Snowflake) =
        addEvent?.getGuildOrNull()?.getRole(id) ?: removeEvent!!.getGuildOrNull()!!.getRole(id)

    inline fun isAddEvent(): Boolean = addEvent == null
    inline fun isRemoveEvent(): Boolean = removeEvent == null
}