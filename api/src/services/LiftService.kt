package me.guillaumewilmot.api.services

import me.guillaumewilmot.api.DB
import me.guillaumewilmot.api.models.Lifts
import me.guillaumewilmot.api.models.LiftModel
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class LiftService {
    suspend fun all(): List<LiftModel> = DB.query {
        Lifts
            .selectAll()
            .mapNotNull { LiftModel.fromRow(it) }
    }

    suspend fun one(id: Long): LiftModel? = DB.query {
        Lifts
            .select { (Lifts.id eq id) }
            .mapNotNull { LiftModel.fromRow(it) }
            .singleOrNull()
    }
}