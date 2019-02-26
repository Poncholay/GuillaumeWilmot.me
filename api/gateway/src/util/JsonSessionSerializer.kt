package me.guillaumewilmot.api.gateway.util

import io.ktor.sessions.SessionSerializer
import me.guillaumewilmot.api.gateway.models.exceptions.HttpInternalErrorException
import me.guillaumewilmot.api.gateway.models.other.SessionModel

class JsonSessionSerializer : SessionSerializer {
    override fun serialize(session: Any): String = session.toJson() ?: throw HttpInternalErrorException()
    override fun deserialize(text: String): Any = text.to<SessionModel>() ?: throw HttpInternalErrorException()
}