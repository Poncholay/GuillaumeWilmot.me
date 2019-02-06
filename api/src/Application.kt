package me.guillaumewilmot.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.header
import me.guillaumewilmot.api.controllers.AuthController
import me.guillaumewilmot.api.controllers.ExerciseController
import me.guillaumewilmot.api.controllers.LiftController
import me.guillaumewilmot.api.models.SessionModel
import me.guillaumewilmot.api.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.models.responses.ResponseModel

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            disableHtmlEscaping()
            serializeNulls()
            setDateFormat("YYYY/MM/DD HH:mm")
        }
    }
    install(Locations)
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
        }
    }
    install(Sessions) {
        header<SessionModel>("Authorization", SessionStorageMemory()) {}
    }

    DB.connect()

    fun Routing.healthCheck() {
        get("/ping") {
            call.respond(ResponseModel("Olejoi !"))
        }
    }

    routing {
        healthCheck()
        AuthController.route(this)
        LiftController.route(this)
        ExerciseController.route(this)
    }
}