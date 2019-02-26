package me.guillaumewilmot.api.gateway.controllers

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import me.guillaumewilmot.api.gateway.MSG_HTTP_200
import me.guillaumewilmot.api.gateway.middlewares.AuthMiddleware
import me.guillaumewilmot.api.gateway.models.exceptions.HttpInternalErrorException
import me.guillaumewilmot.api.gateway.models.other.SessionModel
import me.guillaumewilmot.api.gateway.models.responses.ResponseModel

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object UserController {
    fun route(router: Route) {
        router.route("/user") {
            /**
             * Deletes the user's session
             */
            suspend fun me(call: ApplicationCall) {
                val user = call.sessions.get<SessionModel>()?.user ?: throw HttpInternalErrorException()
                call.respond(ResponseModel(MSG_HTTP_200, user))
            }

            /**
             * ROUTES
             */
            get("/me") { AuthMiddleware.run(call); me(call) }
        }
    }
}