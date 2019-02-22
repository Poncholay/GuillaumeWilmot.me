package me.guillaumewilmot.api.services

import me.guillaumewilmot.api.DB
import me.guillaumewilmot.api.models.LiftModel
import me.guillaumewilmot.api.models.Lifts
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object LiftService {
    /**
     * Gets all lifts
     * @return lift list or null
     */
    suspend fun all(): List<LiftModel> = DB.query {
        Lifts.selectAll()
            .mapNotNull { LiftModel.fromRow(it) }
    }

    /**
     * Gets all lifts
     * @return lift list or null
     */
    suspend fun mine(userId: Int): List<LiftModel> = DB.query {
        Lifts.select { (Lifts.userId eq userId) }
            .mapNotNull { LiftModel.fromRow(it) }
    }

    /**
     * Gets one lift by id
     * @return lift or null
     */
    suspend fun one(id: Int): LiftModel? = DB.query {
        Lifts.select { (Lifts.id eq id) }
            .mapNotNull { LiftModel.fromRow(it) }
            .singleOrNull()
    }

    /**
     * Saves a new lift
     * @return lift or null
     */
    suspend fun save(lift: LiftModel): LiftModel? = DB.query {
        (Lifts.insert { it.fill(lift) } get Lifts.id)?.let { id ->
            return@query lift.also { it.id = id }
        }
        return@query null
    }
}