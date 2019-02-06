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
import me.guillaumewilmot.api.ERROR_INVALID_FORM
import me.guillaumewilmot.api.MSG_HTTP_200
import me.guillaumewilmot.api.MSG_HTTP_404
import me.guillaumewilmot.api.middlewares.AuthMiddleware
import me.guillaumewilmot.api.middlewares.DataAccessCheck
import me.guillaumewilmot.api.models.LiftModel
import me.guillaumewilmot.api.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.models.responses.ResponseModel
import me.guillaumewilmot.api.services.LiftService
import me.guillaumewilmot.api.to

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object LiftController {
    fun route(router: Route) {
        router.route("/lift") {
            /**
             * LOCATIONS
             */
            @Location("/{id}")
            data class LiftId(val id: Int)

            /**
             * @returns all lifts
             */
            suspend fun all(call: ApplicationCall) {
                val lifts = LiftService.all()
                call.respond(ResponseModel(MSG_HTTP_200, lifts))
            }

            /**
             * @returns one lift or null
             */
            suspend fun create(call: ApplicationCall) {
                val requestBody = call.receiveText()
                requestBody.to<LiftModel>()?.let { lift ->
                    try {
                        LiftService.save(lift)?.let { new ->
                            call.respond(ResponseModel(MSG_HTTP_200, new))
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
                    }
                }
                call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(ERROR_INVALID_FORM))
            }

            /**
             * @returns one lift or null
             * @param liftId Contains Id of the lift
             */
            suspend fun one(call: ApplicationCall, liftId: LiftId) {
                LiftService.one(liftId.id)?.let { lift ->
                    DataAccessCheck.run(call, lift.userId)
                    return call.respond(ResponseModel(MSG_HTTP_200, lift))
                }
                call.respond(HttpStatusCode.NotFound, ErrorResponseModel(MSG_HTTP_404))
            }

            /**
             * ROUTES
             */
            get("/") { AuthMiddleware.run(call); all(call) }
            post("/") { AuthMiddleware.run(call); create(call) }
            get<LiftId> { AuthMiddleware.run(call); one(call, it) }
        }
    }
}