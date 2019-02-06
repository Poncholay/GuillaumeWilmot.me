package me.guillaumewilmot.api.models

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement

class LiftModel {
    internal var id: Int = 0
    internal var userId: Int = 0
    internal var exerciseId: Int = 0
    internal var weight: Float = 0.0f
    internal var reps: Int = 0
    internal var date: Long = 0
    internal var imageUrl: String? = null

    companion object {
        fun fromRow(row: ResultRow): LiftModel = LiftModel()
            .apply { id = row[Lifts.id] }
            .apply { userId = row[Lifts.userId] }
            .apply { exerciseId = row[Lifts.exerciseId] }
            .apply { weight = row[Lifts.weight] }
            .apply { reps = row[Lifts.reps] }
            .apply { date = row[Lifts.date] }
            .apply { imageUrl = row[Lifts.imageUrl] }
    }
}

object Lifts : Table() {
    var id = integer("id").autoIncrement().primaryKey()
    var userId = integer("user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    var exerciseId = integer("exercise_id").references(Exercises.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    var weight = float("weight")
    var reps = integer("reps")
    var date = long("date")
    var imageUrl = varchar("image_url", 512)

    fun InsertStatement<Number>.fill(lift: LiftModel) {
        this[exerciseId] = lift.exerciseId
        this[weight] = lift.weight
        this[reps] = lift.reps
        this[date] = lift.date
        this[imageUrl] = lift.imageUrl ?: ""
    }
}