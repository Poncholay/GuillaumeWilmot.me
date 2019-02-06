package me.guillaumewilmot.api.middlewares

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.AttributeKey
import me.guillaumewilmot.api.ERROR_INVALID_TOKEN
import me.guillaumewilmot.api.ERROR_NO_TOKEN
import me.guillaumewilmot.api.models.SessionModel
import me.guillaumewilmot.api.models.exceptions.HttpForbiddenException

object AuthMiddleware {
    fun run(call: ApplicationCall) {
        call.attributes.getOrNull(AttributeKey("SessionId")) ?: throw HttpForbiddenException(ERROR_NO_TOKEN)
        call.sessions.get<SessionModel>() ?: throw HttpForbiddenException(ERROR_INVALID_TOKEN)
    }
}