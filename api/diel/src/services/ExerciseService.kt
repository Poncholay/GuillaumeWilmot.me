package me.guillaumewilmot.api.diel.services

import me.guillaumewilmot.api.gateway.DB
import me.guillaumewilmot.api.diel.models.ExerciseModel
import me.guillaumewilmot.api.diel.models.Exercises
import me.guillaumewilmot.api.diel.models.Lifts
import org.jetbrains.exposed.sql.*

object ExerciseService {
    /**
     * Gets all exercises
     * @return exercise list or null
     */
    suspend fun all(): List<ExerciseModel> = DB.query {
        (Exercises leftJoin Lifts).slice(*Exercises.fields.toTypedArray(), Lifts.exerciseId.count())
            .selectAll()
            .groupBy(Exercises.id)
            .mapNotNull { ExerciseModel.fromRow(it).apply { timesLogged = it[Lifts.exerciseId.count()] } }
    }

    /**
     * Gets one exercise
     * @return exercise or null
     */
    suspend fun one(id: Int): ExerciseModel? = DB.query {
        (Exercises leftJoin Lifts).slice(*Exercises.fields.toTypedArray(), Lifts.exerciseId.countDistinct())
            .select { (Exercises.id eq id) }
            .groupBy(Exercises.id)
            .mapNotNull { ExerciseModel.fromRow(it).apply { timesLogged = it[Lifts.exerciseId.count()] } }
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