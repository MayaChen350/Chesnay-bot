package io.github.mayachen350.chesnaybot.backend

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.IntEntity

class RolesGivenToMemberEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : ImmutableEntityClass<Int, RolesGivenToMemberEntity>(RolesGivenToMemberTable)

    val userId by RolesGivenToMemberTable.userId
    val roleId by RolesGivenToMemberTable.roleId
}