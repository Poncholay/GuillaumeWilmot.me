package me.guillaumewilmot.api.controllers

import io.ktor.application.call
import io.ktor.auth.OAuthAccessTokenResponse
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.*
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.param
import me.guillaumewilmot.api.AuthConfig
import me.guillaumewilmot.api.HTTP_200_MSG
import me.guillaumewilmot.api.HTTP_400_MSG
import me.guillaumewilmot.api.models.ErrorResponseModel
import me.guillaumewilmot.api.models.ResponseModel

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
fun Route.AuthController() {
    authenticate("googleOAuth") {
        location<AuthConfig.Login> {
            param("error") {
                handle {
                    println("When is this shiz called")
                }
            }

            handle {
                val principal = call.authentication.principal<OAuthAccessTokenResponse>()
                if (principal != null) {
                    call.respond(ResponseModel(HTTP_200_MSG, principal))
                } else {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(HTTP_400_MSG))
                }
            }
        }
    }
}