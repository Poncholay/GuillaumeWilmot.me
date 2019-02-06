package me.guillaumewilmot.api.controllers

import io.ktor.application.ApplicationCall
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
import me.guillaumewilmot.api.MSG_HTTP_200
import me.guillaumewilmot.api.MSG_HTTP_400
import me.guillaumewilmot.api.MSG_HTTP_404
import me.guillaumewilmot.api.models.ExerciseModel
import me.guillaumewilmot.api.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.models.responses.ResponseModel
import me.guillaumewilmot.api.services.ExerciseService
import me.guillaumewilmot.api.to

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object ExerciseController {
    fun route(router: Route) {
        router.route("/exercise") {
            /**
             * LOCATIONS
             */
            @Location("/{id}")
            data class ExerciseId(val id: Int)

            /**
             * @returns all exercises
             */
            suspend fun all(call: ApplicationCall) {
                val exercises = ExerciseService.all()
                call.respond(ResponseModel(MSG_HTTP_200, exercises))
            }

            /**
             * @returns one exercise or null
             */
            suspend fun create(call: ApplicationCall) {
                val requestBody = call.receiveText()
                requestBody.to<ExerciseModel>()?.let { exercise ->
                    try {
                        ExerciseService.save(exercise)?.let { new ->
                            call.respond(ResponseModel(MSG_HTTP_200, new))
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
                    }
                }
                call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(MSG_HTTP_400))
            }

            /**
             * @returns one exercise or null
             * @param exerciseId Contains Id of the exercise
             */
            suspend fun one(call: ApplicationCall, exerciseId: ExerciseId) {
                ExerciseService.one(exerciseId.id)?.let { exercise ->
                    return call.respond(ResponseModel(MSG_HTTP_200, exercise))
                }
                call.respond(HttpStatusCode.NotFound, ErrorResponseModel(MSG_HTTP_404))
            }

            /**
             * ROUTES
             */
            get("/") { all(call) }
            post("/") { create(call) }
            get<ExerciseId> { one(call, it) }
        }
    }
}