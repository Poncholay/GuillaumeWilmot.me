package me.guillaumewilmot.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import me.guillaumewilmot.api.controllers.ExerciseController
import me.guillaumewilmot.api.controllers.LiftController
import me.guillaumewilmot.api.models.ResponseModel
import me.guillaumewilmot.api.services.LiftService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            disableHtmlEscaping()
            serializeNulls()
        }
    }
    install(Locations)

    fun Routing.healthCheck() {
        get("/ping") {
            call.respond(ResponseModel("Olejoi !"))
        }
    }

    routing {
        healthCheck()
        LiftController(LiftService()) //TODO : Service dependencies
        ExerciseController()
    }
}