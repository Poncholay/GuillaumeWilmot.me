package me.guillaumewilmot.api.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

class LiftModel {
    private var id: Long = 0
    private var exerciseId: Long = 0
    private var weight: Float = 0.0f
    private var reps: Int = 0
    private var date: DateTime = DateTime()
    private var imageUrl: String? = null

    companion object {
        fun fromRow(row: ResultRow): LiftModel = LiftModel()
            .apply { id = row[Lifts.id] }
            .apply { exerciseId = row[Lifts.exerciseId] }
            .apply { weight = row[Lifts.weight] }
            .apply { reps = row[Lifts.reps] }
            .apply { date = row[Lifts.date] }
            .apply { imageUrl = row[Lifts.imageUrl] }
    }
}

object Lifts : Table() {
    var id = long("id").primaryKey().autoIncrement()
    var exerciseId = long("exercise_id")/*.uniqueIndex().references()*/
    var weight = float("weight")
    var reps = integer("reps")
    var date = date("date")
    var imageUrl = varchar("image_url", 512)
}