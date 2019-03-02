package me.guillaumewilmot.api.diel.controllers

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
import me.guillaumewilmot.api.diel.models.ExerciseModel
import me.guillaumewilmot.api.diel.services.ExerciseService
import me.guillaumewilmot.api.gateway.ERROR_INVALID_FORM
import me.guillaumewilmot.api.gateway.MSG_HTTP_200
import me.guillaumewilmot.api.gateway.MSG_HTTP_404
import me.guillaumewilmot.api.gateway.util.to
import me.guillaumewilmot.api.gateway.middlewares.AuthMiddleware
import me.guillaumewilmot.api.gateway.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.gateway.models.responses.ResponseModel

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
                            return call.respond(ResponseModel(MSG_HTTP_200, new))
                        }
                    } catch (e: Exception) {
                        return call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
                    }
                }
                call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(ERROR_INVALID_FORM))
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
            get("/") { AuthMiddleware.run(call); all(call) }
            post("/") { AuthMiddleware.run(call); create(call) }
            get<ExerciseId> { AuthMiddleware.run(call); one(call, it) }
        }
    }
}