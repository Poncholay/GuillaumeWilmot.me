package me.guillaumewilmot.api.middlewares

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import me.guillaumewilmot.api.models.SessionModel
import me.guillaumewilmot.api.models.exceptions.HttpForbiddenException

object AuthMiddleware {
    fun run(call: ApplicationCall) = call.sessions.get<SessionModel>() ?: throw HttpForbiddenException()
}