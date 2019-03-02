package me.guillaumewilmot.api.diel.models

import me.guillaumewilmot.api.gateway.models.Users
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement

class LiftModel {
    var id: Int = -1
    var userId: Int = 0
    var exerciseId: Int = 0
    var weight: Float = 0.0f
    var reps: Int = 0
    var date: Long = 0

    companion object {
        fun fromRow(row: ResultRow): LiftModel = LiftModel()
            .apply { id = row[Lifts.id] }
            .apply { userId = row[Lifts.userId] }
            .apply { exerciseId = row[Lifts.exerciseId] }
            .apply { weight = row[Lifts.weight] }
            .apply { reps = row[Lifts.reps] }
            .apply { date = row[Lifts.date] }
    }
}

object Lifts : Table() {
    var id = integer("id").autoIncrement().primaryKey()
    var userId = integer("user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    var exerciseId = integer("exercise_id").references(Exercises.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    var weight = float("weight")
    var reps = integer("reps")
    var date = long("date")

    fun InsertStatement<Number>.fill(lift: LiftModel) {
        if (lift.id != -1) {
            this[id] = lift.id
        }
        this[userId] = lift.userId
        this[exerciseId] = lift.exerciseId
        this[weight] = lift.weight
        this[reps] = lift.reps
        this[date] = lift.date
    }
}