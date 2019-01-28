package me.guillaumewilmot.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.oauth
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.locations.url
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import me.guillaumewilmot.api.controllers.AuthController
import me.guillaumewilmot.api.controllers.ExerciseController
import me.guillaumewilmot.api.controllers.LiftController
import me.guillaumewilmot.api.models.ErrorResponseModel
import me.guillaumewilmot.api.models.ResponseModel

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