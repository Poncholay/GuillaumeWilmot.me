package me.guillaumewilmot.api.controllers

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import me.guillaumewilmot.api.models.ResponseModel
import me.guillaumewilmot.api.services.LiftService

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
fun Route.LiftController(liftService: LiftService) {
    route("/lift") {
        /**
         * @returns all lifts
         */
        get("/") {
            val lifts = liftService.all()
            call.respond(ResponseModel("Success", lifts))
        }

        /**
         * @returns one lift or null
         * @param id Id of the lift
         */
        @Location("/{id}")
        data class LiftId(val id: Long)
        get<LiftId> {
            val lift = liftService.one(it.id)
            call.respond(ResponseModel("Success", lift))
        }
    }
}