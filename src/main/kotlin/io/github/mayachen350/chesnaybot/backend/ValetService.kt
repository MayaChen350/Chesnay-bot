package io.github.mayachen350.chesnaybot.backend

import io.github.mayachen350.chesnaybot.backend.RolesGivenToMemberTable.roleId
import io.github.mayachen350.chesnaybot.backend.RolesGivenToMemberTable.userId
import io.github.mayachen350.chesnaybot.resources.DebugProdStrings
import io.r2dbc.spi.ConnectionFactoryOptions
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.dao.EntityClass
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.deleteWhere
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransactionAsync
import java.util.*


object ValetService {
    val db by lazy {
        R2dbcDatabase.connect(
            DebugProdStrings.DB_CONNECTION,
            databaseConfig = {
                connectionFactoryOptions {
                    option(ConnectionFactoryOptions.USER, "Maya")
                    option(ConnectionFactoryOptions.PASSWORD, "Furina")
                }
            }
        )
    }

    suspend fun getAllSavedReactionRoles(): Deferred<Queue<RolesGivenToMemberEntity>> =
        suspendTransactionAsync(db = db) {
            LinkedList(RolesGivenToMemberEntity.all().toList())
        }

    suspend fun hasAddedUserRole(pUserId: ULong, pRoleId: ULong): Deferred<Boolean> =
        suspendTransactionAsync(db = db) {
            RolesGivenToMemberTable.selectAll()
                .where((userId eq pUserId) and (roleId eq pRoleId))
                .empty().not()
        }

    suspend fun saveRoleAdded(pUserId: ULong, pRoleId: ULong): Unit = withContext(Dispatchers.IO) {
        suspendTransaction(db = db) {
            RolesGivenToMemberTable.insert {
                it[userId] = pUserId
                it[roleId] = pRoleId
            }
        }
    }

    suspend fun saveRoleRemoved(pUserId: ULong, pRoleId: ULong): Unit = withContext(Dispatchers.IO) {
        suspendTransaction(db = db) {
            RolesGivenToMemberTable.deleteWhere {
                (this.userId eq pUserId) and (this.roleId eq pRoleId)
            }
        }
    }

    suspend fun removeAllSavedRolesFromMember(pUserId: ULong): Unit = withContext(Dispatchers.IO) {
        suspendTransaction(db = db) {
            RolesGivenToMemberTable.deleteWhere {
                this.userId eq pUserId
            }
        }
    }
}