package me.guillaumewilmot.api.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement

class ExerciseModel {
    var id: Int = 0
    var name: String = ""

    companion object {
        fun fromRow(row: ResultRow): ExerciseModel = ExerciseModel()
            .apply { id = row[Exercises.id] }
            .apply { name = row[Exercises.name] }
    }
}

object Exercises : Table() {
    var id = integer("id").autoIncrement().primaryKey()
    var name = varchar("name", 50).uniqueIndex()

    fun InsertStatement<Number>.fill(exercise: ExerciseModel) {
        if (exercise.id != -1) {
            this[id] = exercise.id
        }
        this[Exercises.name] = exercise.name
    }
}