package me.guillaumewilmot.api

import io.ktor.auth.OAuthServerSettings
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location

object AuthConfig {
    @KtorExperimentalLocationsAPI
    @Location("/auth/login/{type?}")
    data class Login(val type: String = "")

    val loginProviders = mapOf(
        "google" to OAuthServerSettings.OAuth2ServerSettings(
            name = "google",
            authorizeUrl = "https://github.com/login/oauth/authorize",
            accessTokenUrl = "https://github.com/login/oauth/access_token",
            clientId = "***",
            clientSecret = "***"
        )
    )
}
