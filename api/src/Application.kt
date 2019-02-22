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
import io.ktor.sessions.*
import io.ktor.util.KtorExperimentalAPI
import me.guillaumewilmot.api.controllers.AuthController
import me.guillaumewilmot.api.controllers.ExerciseController
import me.guillaumewilmot.api.controllers.LiftController
import me.guillaumewilmot.api.controllers.UserController
import me.guillaumewilmot.api.models.exceptions.HttpInternalErrorException
import me.guillaumewilmot.api.models.other.SessionModel
import me.guillaumewilmot.api.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.models.responses.ResponseModel
import me.guillaumewilmot.api.util.to
import me.guillaumewilmot.api.util.toJson
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
                is me.guillaumewilmot.api.models.exceptions.HttpException -> call.respond(
                    e.code,
                    ErrorResponseModel(e.message)
                )
                else -> call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
            }
        }
    }
    install(Sessions) {
        header<SessionModel>("Authorization", directorySessionStorage(File("sessions"), false)) {
            transform(object : SessionTransportTransformer {
                override fun transformRead(transportValue: String): String? = transportValue.removePrefix("Bearer ")

                override fun transformWrite(transportValue: String): String = "Bearer $transportValue".also {
                    println(transportValue)
                }
            })
            serializer = object : SessionSerializer {
                override fun serialize(session: Any): String =
                    session.toJson() ?: "".also { throw HttpInternalErrorException() }

                override fun deserialize(text: String): Any =
                    text.to<SessionModel>() ?: Any().apply { throw HttpInternalErrorException() }
            }
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
        UserController.route(this)
        AuthController.route(this)
        LiftController.route(this)
        ExerciseController.route(this)
    }
}
