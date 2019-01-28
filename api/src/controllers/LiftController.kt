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
import me.guillaumewilmot.api.MSG_HTTP_200
import me.guillaumewilmot.api.MSG_HTTP_400
import me.guillaumewilmot.api.MSG_HTTP_404
import me.guillaumewilmot.api.models.ErrorResponseModel
import me.guillaumewilmot.api.models.LiftModel
import me.guillaumewilmot.api.models.ResponseModel
import me.guillaumewilmot.api.services.LiftService
import me.guillaumewilmot.api.to

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object LiftController {
    fun route(router: Route) {
        router.route("/lift") {
            /**
             * @returns all lifts
             */
            get("/") {
                val lifts = LiftService.all()
                call.respond(ResponseModel(MSG_HTTP_200, lifts))
            }

            /**
             * @returns one lift or null
             */
            post("/") {
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
                call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(MSG_HTTP_400)) //TODO : error
            }

            /**
             * @returns one lift or null
             * @param id Id of the lift
             */
            @Location("/{id}")
            data class LiftId(val id: Int)
            get<LiftId> {
                LiftService.one(it.id)?.let { lift ->
                    return@get call.respond(ResponseModel(MSG_HTTP_200, lift))
                }
                call.respond(HttpStatusCode.NotFound, ErrorResponseModel(MSG_HTTP_404))
            }
        }
    }
}