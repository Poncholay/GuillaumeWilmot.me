package me.guillaumewilmot.api.util

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import me.guillaumewilmot.api.ERROR_FORBIDDEN
import me.guillaumewilmot.api.models.exceptions.HttpForbiddenException
import me.guillaumewilmot.api.models.other.SessionModel

object DataAccessCheck {
    /**
     * Verifies the appartenance of en entity to the request's sender
     */
    fun run(call: ApplicationCall, dataUserId: Int) {
        if (call.sessions.get<SessionModel>()?.user?.id != dataUserId) {
            throw HttpForbiddenException(ERROR_FORBIDDEN)
        }
    }
}