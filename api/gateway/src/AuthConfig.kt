package me.guillaumewilmot.api.gateway

object AuthConfig {
    data class Client(val clientId: String = "", val clientSecret: String = "")

    val providers = mapOf(
        "google" to Client(
            clientId = "294753139295-m7bsqrdj8q8gomitc54knag0rnguopf3.apps.googleusercontent.com"
        )
    )
}
