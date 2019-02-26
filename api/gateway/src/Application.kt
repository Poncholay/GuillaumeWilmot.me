package me.guillaumewilmot.api.gateway

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
import io.ktor.sessions.Sessions
import io.ktor.sessions.directorySessionStorage
import io.ktor.sessions.header
import io.ktor.util.KtorExperimentalAPI
import me.guillaumewilmot.api.gateway.DB.simpleQuery
import me.guillaumewilmot.api.gateway.controllers.AuthController
import me.guillaumewilmot.api.gateway.controllers.UserController
import me.guillaumewilmot.api.gateway.models.Users
import me.guillaumewilmot.api.gateway.models.other.SessionModel
import me.guillaumewilmot.api.gateway.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.gateway.models.responses.ResponseModel
import me.guillaumewilmot.api.gateway.util.BearerSessionTransportTransformer
import me.guillaumewilmot.api.gateway.util.JsonSessionSerializer
import org.jetbrains.exposed.sql.SchemaUtils
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
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
            e.printStackTrace()
            when (e) {
                is me.guillaumewilmot.api.gateway.models.exceptions.HttpException -> call.respond(
                        e.code,
                        ErrorResponseModel(e.message)
                )
                else -> call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
            }
        }
    }
    install(Sessions) {
        header<SessionModel>("Authorization", directorySessionStorage(File("sessions"), false)) {
            transform(BearerSessionTransportTransformer())
            serializer = JsonSessionSerializer()
        }
    }

    DB.connect()
    simpleQuery {
        /**
         * Creates the database tables
         */
        SchemaUtils.create(Users)
    }

    fun Routing.healthCheck() {
        get("/ping") {
            call.respond(ResponseModel("Gateway !"))
        }
    }

    routing {
        healthCheck()
        UserController.route(this)
        AuthController.route(this)
    }
}