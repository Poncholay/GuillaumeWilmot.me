package me.guillaumewilmot.api.services

import me.guillaumewilmot.api.DB
import me.guillaumewilmot.api.models.ExerciseModel
import me.guillaumewilmot.api.models.Exercises
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object ExerciseService {
    /**
     * Gets all exercises
     * @return exercise list or null
     */
    suspend fun all(): List<ExerciseModel> = DB.query {
        Exercises.selectAll()
            .mapNotNull { ExerciseModel.fromRow(it) }
    }

    /**
     * Gets one exercise
     * @return exercise or null
     */
    suspend fun one(id: Int): ExerciseModel? = DB.query {
        Exercises.select { (Exercises.id eq id) }
            .mapNotNull { ExerciseModel.fromRow(it) }
            .singleOrNull()
    }

    /**
     * Saves a new exercise
     * @return exercise or null
     */
    suspend fun save(exercise: ExerciseModel): ExerciseModel? = DB.query {
        (Exercises.insert { it.fill(exercise) } get Exercises.id)?.let { id ->
            return@query exercise.also { it.id = id }
        }
        return@query null
    }
}