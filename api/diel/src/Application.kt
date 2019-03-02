package me.guillaumewilmot.api.diel

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
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
import me.guillaumewilmot.api.diel.controllers.ExerciseController
import me.guillaumewilmot.api.diel.controllers.LiftController
import me.guillaumewilmot.api.diel.models.Exercises
import me.guillaumewilmot.api.diel.models.Lifts
import me.guillaumewilmot.api.gateway.MSG_HTTP_404
import me.guillaumewilmot.api.gateway.config.DB
import me.guillaumewilmot.api.gateway.models.other.SessionModel
import me.guillaumewilmot.api.gateway.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.gateway.models.responses.ResponseModel
import me.guillaumewilmot.api.gateway.util.BearerSessionTransportTransformer
import me.guillaumewilmot.api.gateway.util.JsonSessionSerializer
import org.jetbrains.exposed.sql.SchemaUtils
import org.slf4j.event.Level
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
    install(CallLogging) { level = Level.INFO }
    install(Locations)
    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound, ErrorResponseModel(MSG_HTTP_404))
        }
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
    DB.simpleQuery {
        /**
         * Creates the database tables
         */
        SchemaUtils.create(Lifts)
        SchemaUtils.create(Exercises)
    }


    fun Routing.healthCheck() {
        get("/ping") {
            call.respond(ResponseModel("Do I even lift !"))
        }
    }

    routing {
        healthCheck()
        LiftController.route(this)
        ExerciseController.route(this)
    }
}
