package me.guillaumewilmot.api

import io.ktor.auth.OAuthServerSettings
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location

object AuthConfig {
    data class Client(val clientId: String = "", val clientSecret: String = "")

    val providers = mapOf(
        "google" to Client(
            clientId = "294753139295-m7bsqrdj8q8gomitc54knag0rnguopf3.apps.googleusercontent.com",
            clientSecret = "OXshERyBqV3Jq7iqBlnZRc3s"
        )
    )
}
