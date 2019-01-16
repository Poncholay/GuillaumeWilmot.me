package me.guillaumewilmot.api.controllers

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import me.guillaumewilmot.api.HTTP_200_MSG
import me.guillaumewilmot.api.HTTP_400_MSG
import me.guillaumewilmot.api.HTTP_404_MSG
import me.guillaumewilmot.api.models.ErrorResponseModel
import me.guillaumewilmot.api.models.ExerciseModel
import me.guillaumewilmot.api.models.ResponseModel
import me.guillaumewilmot.api.services.ExerciseService
import me.guillaumewilmot.api.to
import java.sql.SQLException

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
fun Route.ExerciseController() {
    route("/exercise") {
        /**
         * @returns all exercises
         */
        get("/") {
            val exercises = ExerciseService.all()
            call.respond(ResponseModel(HTTP_200_MSG, exercises))
        }

        /**
         * @returns one exercise or null
         * @param id Id of the exercise
         */
        post("/") {
            val requestBody = call.receiveText()
            requestBody.to<ExerciseModel>()?.let { exercise ->
                try {
                    ExerciseService.save(exercise)?.let { new ->
                        call.respond(ResponseModel(HTTP_200_MSG, new))
                    }
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(e.message))
                }
            }
            call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(HTTP_400_MSG)) //TODO : error
        }

        /**
         * @returns one exercise or null
         * @param id Id of the exercise
         */
        @Location("/{id}")
        data class ExerciseId(val id: Int)
        get<ExerciseId> {
            ExerciseService.one(it.id)?.let { exercise ->
                return@get call.respond(ResponseModel(HTTP_200_MSG, exercise))
            }
            call.respond(HttpStatusCode.NotFound, ErrorResponseModel(HTTP_404_MSG))
        }
    }
}