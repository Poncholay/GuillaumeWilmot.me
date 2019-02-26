package me.guillaumewilmot.api.gateway.util

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import me.guillaumewilmot.api.gateway.ERROR_FORBIDDEN
import me.guillaumewilmot.api.gateway.models.exceptions.HttpForbiddenException
import me.guillaumewilmot.api.gateway.models.other.SessionModel

object DataAccessCheck {
    /**
     * Verifies the belonging of an entity to the request's sender
     */
    fun run(call: ApplicationCall, dataUserId: Int) {
        if (call.sessions.get<SessionModel>()?.user?.id != dataUserId) {
            throw HttpForbiddenException(ERROR_FORBIDDEN)
        }
    }
}