package me.guillaumewilmot.api.gateway.util

import io.ktor.sessions.SessionTransportTransformer

class BearerSessionTransportTransformer : SessionTransportTransformer {
    override fun transformRead(transportValue: String): String? = transportValue.removePrefix("Bearer ")
    override fun transformWrite(transportValue: String): String = "Bearer $transportValue".also {
        println(transportValue)
    }
}