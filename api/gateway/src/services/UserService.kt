package me.guillaumewilmot.api.gateway.services

import me.guillaumewilmot.api.gateway.DB
import me.guillaumewilmot.api.gateway.models.UserModel
import me.guillaumewilmot.api.gateway.models.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object UserService {
    /**
     * Gets all Users
     * @return User list or null
     */
    suspend fun all(): List<UserModel> = DB.query {
        Users.selectAll()
            .mapNotNull { UserModel.fromRow(it) }
    }

    /**
     * Gets one User
     * @return User or null
     */
    suspend fun one(id: Int): UserModel? = DB.query {
        Users.select { (Users.id eq id) }
            .mapNotNull { UserModel.fromRow(it) }
            .singleOrNull()
    }

    /**
     * Gets one User
     * @return User or null
     */
    suspend fun one(googleId: String): UserModel? = DB.query {
        Users.select { (Users.googleId eq googleId) }
            .mapNotNull { UserModel.fromRow(it) }
            .singleOrNull()
    }

    /**
     * Saves a new User
     * @return User or null
     */
    suspend fun save(user: UserModel): UserModel? = DB.query {
        (Users.insert { it.fill(user) } get Users.id)?.let { id ->
            return@query user.also { it.id = id }
        }
        return@query null
    }
}