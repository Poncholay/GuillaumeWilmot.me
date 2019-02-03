package me.guillaumewilmot.api.controllers

import com.google.api.client.auth.openidconnect.IdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.ApacheHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import me.guillaumewilmot.api.*
import me.guillaumewilmot.api.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.models.responses.ResponseModel
import me.guillaumewilmot.api.models.forms.GoogleAuthModel
import me.guillaumewilmot.api.models.responses.SigninResponseModel
import java.util.*

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object AuthController {
    fun route(router: Route) {
        router.route("/auth") {
            /**
             * Logs a user in. User is first registered if no matching account is found.
             * @return String : AccessToken to use this API
             */
            fun loginOrRegister(payload: IdToken.Payload): String {
                //TODO : Register him or log him in
                val userId = payload.subject
                val email = payload["email"]
                val emailVerified = payload["emailVerified"] == "true"
                val name = payload["name"] as String
                val pictureUrl = payload["picture"] as String
                val locale = payload["locale"] as String
                val familyName = payload["family_name"] as String
                val givenName = payload["given_name"] as String
                return "coucou"
            }

            /**
             * Verifies an authentication via google
             * @returns a token
             */
            post("/login/google") {
                val requestBody = call.receiveText()
                requestBody.to<GoogleAuthModel>()?.let { auth ->
                    try {
                        val veryfierAndroid = GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), JacksonFactory())
                            .setAudience(Arrays.asList(AuthConfig.providers["google"]?.clientId))
                            .setIssuer("https://accounts.google.com")
                            .setAcceptableTimeSkewSeconds(1L)
                            .build()
                        val veryfierOther = GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), JacksonFactory())
                            .setAudience(Arrays.asList(AuthConfig.providers["google"]?.clientId))
                            .setIssuer("accounts.google.com")
                            .setAcceptableTimeSkewSeconds(1L)
                            .build()
                        val idToken = veryfierAndroid.verify(auth.idToken) ?: veryfierOther.verify(auth.idToken)
                        if (idToken != null) {
                            val payload = idToken.payload
                            if (payload != null) {
                                val accessToken = loginOrRegister(payload)
                                call.respond(ResponseModel(MSG_LOGIN_SUCCESS, SigninResponseModel(accessToken)))
                                return@post
                            }
                        }
                        call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(ERROR_INVALID_ID_TOKEN))
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, ErrorResponseModel(e.toString()))
                    }
                } ?: run {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponseModel(MSG_HTTP_400))
                }
            }
        }
    }
}