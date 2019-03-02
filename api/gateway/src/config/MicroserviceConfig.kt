package me.guillaumewilmot.api.gateway.config

object MicroserviceConfig {
    data class Service(val port: Int = 0)

    val services = mapOf(
            "powerlifting" to Service(port = 9060)
    )
}
