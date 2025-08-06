package io.github.mayachen350.chesnaybot.bot.backend

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object RolesGivenToMemberTable : IntIdTable("rolesGivenToMember") {
    val userId = ulong("userId")
    val roleId = ulong("roleId")
}