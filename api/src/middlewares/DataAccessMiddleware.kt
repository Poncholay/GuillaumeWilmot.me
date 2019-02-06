package me.guillaumewilmot.api.middlewares

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import me.guillaumewilmot.api.ERROR_FORBIDDEN
import me.guillaumewilmot.api.models.SessionModel
import me.guillaumewilmot.api.models.exceptions.HttpForbiddenException

object DataAccessCheck {
    fun run(call: ApplicationCall, dataUserId: Int) {
        if (call.sessions.get<SessionModel>()?.id != dataUserId) {
            throw HttpForbiddenException(ERROR_FORBIDDEN)
        }
    }
}