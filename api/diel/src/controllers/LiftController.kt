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
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import me.guillaumewilmot.api.diel.models.LiftModel
import me.guillaumewilmot.api.diel.services.LiftService
import me.guillaumewilmot.api.gateway.ERROR_INVALID_FORM
import me.guillaumewilmot.api.gateway.MSG_HTTP_200
import me.guillaumewilmot.api.gateway.MSG_HTTP_404
import me.guillaumewilmot.api.gateway.util.to
import me.guillaumewilmot.api.gateway.middlewares.AuthMiddleware
import me.guillaumewilmot.api.gateway.models.exceptions.HttpInternalErrorException
import me.guillaumewilmot.api.gateway.models.other.SessionModel
import me.guillaumewilmot.api.gateway.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.gateway.models.responses.ResponseModel
import me.guillaumewilmot.api.gateway.util.DataAccessCheck

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
                call.respond(ResponseModel(MSG_HTTP_200, LiftService.all()))
            }

            /**
             * @returns all user's lifts
             */
            suspend fun mine(call: ApplicationCall) {
                val userId = call.sessions.get<SessionModel>()?.user?.id ?: throw HttpInternalErrorException()
                call.respond(ResponseModel(MSG_HTTP_200, LiftService.mine(userId)))
            }

            /**
             * @returns one lift or null
             */
            suspend fun create(call: ApplicationCall) {
                val requestBody = call.receiveText()
                requestBody.to<LiftModel>()?.let { lift ->
                    try {
                        lift.userId = call.sessions.get<SessionModel>()?.user?.id ?: throw HttpInternalErrorException()
                        LiftService.save(lift)?.let { new ->
                            return call.respond(ResponseModel(MSG_HTTP_200, new))
                        }
                    } catch (e: Exception) {
                        return call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
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
            get("/mine") { AuthMiddleware.run(call); mine(call) }
            get<LiftId> { AuthMiddleware.run(call); one(call, it) }
        }
    }
}