package me.guillaumewilmot.api.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserModel {
    internal var id: Int = 0
    internal var firstname: String = ""
    internal var lastname: String = ""
    internal var email: String = ""
    internal var googleId: String = ""
    internal var pictureUrl: String = ""

    companion object {
        fun fromRow(row: ResultRow): UserModel = UserModel()
            .apply { id = row[Users.id] }
            .apply { firstname = row[Users.firstname] }
            .apply { lastname = row[Users.lastname] }
            .apply { email = row[Users.email] }
            .apply { googleId = row[Users.googleId] }
            .apply { pictureUrl = row[Users.pictureUrl] }
    }
}

object Users : Table() {
    var id = integer("id").autoIncrement().primaryKey()
    var firstname = varchar("firstname", 50)
    var lastname = varchar("lastname", 50)
    var email = varchar("email", 200).uniqueIndex()
    var googleId = varchar("google_id", 200).uniqueIndex()
    var pictureUrl = varchar("picture_url", 200)

    fun InsertStatement<Number>.fill(user: UserModel) {
        this[Users.firstname] = user.firstname
        this[Users.lastname] = user.lastname
        this[Users.email] = user.email
        this[Users.googleId] = user.googleId
        this[Users.pictureUrl] = user.pictureUrl
    }
}