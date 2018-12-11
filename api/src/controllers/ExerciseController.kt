package me.guillaumewilmot.api.controllers

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import me.guillaumewilmot.api.models.ErrorResponseModel
import me.guillaumewilmot.api.models.ResponseModel

@Suppress("FunctionName")
fun Route.ExerciseController() {
    get("/") {
        call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
    }

    get("/success") {
        call.respond(ResponseModel("Hello", "World"))
    }

    get("/error") {
        call.respond(ErrorResponseModel("Error"))
    }
}