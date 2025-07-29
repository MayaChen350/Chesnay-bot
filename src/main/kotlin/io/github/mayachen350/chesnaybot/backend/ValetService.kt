package io.github.mayachen350.chesnaybot.backend

import io.github.mayachen350.chesnaybot.backend.RolesGivenToMemberTable.roleId
import io.github.mayachen350.chesnaybot.backend.RolesGivenToMemberTable.userId
import io.github.mayachen350.chesnaybot.resources.DebugProdStrings
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*


object ValetService {
    val db by lazy {
        Database.connect(
            DebugProdStrings.DB_CONNECTION,
            "com.mysql.cj.jdbc.Driver", // hopefully will get rid of jdbc
            "Maya",
            "Furina"
        )
    }

    @Suppress("DEPRECATION")
    suspend fun getAllSavedReactionRoles(): Deferred<Queue<RolesGivenToMemberEntity>> =
        suspendedTransactionAsync(db = db) {
            LinkedList(RolesGivenToMemberEntity.all().toList())
        }

    @Suppress("DEPRECATION")
    suspend fun hasAddedUserRole(pUserId: ULong, pRoleId: ULong): Deferred<Boolean> = suspendedTransactionAsync(db = db) {
        RolesGivenToMemberTable.selectAll()
            .where((userId eq pUserId) and (roleId eq pRoleId))
            .any()
    }

    suspend fun saveRoleAdded(pUserId: ULong, pRoleId: ULong): Unit = withContext(Dispatchers.IO) {
        transaction(db) {
            RolesGivenToMemberTable.insert {
                it[userId] = pUserId
                it[roleId] = pRoleId
            }
        }
    }

    suspend fun saveRoleRemoved(pUserId: ULong, pRoleId: ULong): Unit = withContext(Dispatchers.IO) {
        transaction(db) {
            RolesGivenToMemberTable.deleteWhere {
                (this.userId eq pUserId) and (this.roleId eq pRoleId)
            }
        }
    }

    suspend fun removeAllSavedRolesFromMember(pUserId: ULong): Unit = withContext(Dispatchers.IO) {
        transaction(db) {
            RolesGivenToMemberTable.deleteWhere {
                this.userId eq pUserId
            }
        }
    }
}