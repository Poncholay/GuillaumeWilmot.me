package me.guillaumewilmot.api.gateway.controllers

import com.google.api.client.auth.openidconnect.IdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.ApacheHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.clear
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import me.guillaumewilmot.api.gateway.AuthConfig
import me.guillaumewilmot.api.gateway.ERROR_INVALID_ID_TOKEN
import me.guillaumewilmot.api.gateway.MSG_HTTP_400
import me.guillaumewilmot.api.gateway.MSG_LOGIN_SUCCESS
import me.guillaumewilmot.api.gateway.models.UserModel
import me.guillaumewilmot.api.gateway.models.forms.GoogleAuthModel
import me.guillaumewilmot.api.gateway.models.other.SessionModel
import me.guillaumewilmot.api.gateway.models.responses.ErrorResponseModel
import me.guillaumewilmot.api.gateway.models.responses.ResponseModel
import me.guillaumewilmot.api.gateway.services.UserService
import me.guillaumewilmot.api.gateway.util.to
import java.time.Instant
import java.util.*

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object AuthController {
    fun route(router: Route) {
        router.route("/auth") {
            /**
             * Returns a user from its googleId, registers him first if no match.
             * @return UserModel
             */
            suspend fun getOrRegister(payload: IdToken.Payload): UserModel {
                var user = UserService.one(payload.subject)
                if (user == null) {
                    user = UserModel()
                    user.googleId = payload.subject
                    user.email = payload["email"] as String
                    user.pictureUrl = payload["picture"] as String
                    user.lastname = payload["family_name"] as String
                    user.firstname = payload["given_name"] as String
                    UserService.save(user)
                }
                return user
            }

            /**
             * Authenticates via Google
             * @returns String : An access token for later calls
             */
            suspend fun googleLogin(call: ApplicationCall) {
                val requestBody = call.receiveText()
                requestBody.to<GoogleAuthModel>()?.let { auth ->
                    try {
                        val verifierAndroid = GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), JacksonFactory())
                            .setAudience(Arrays.asList(AuthConfig.providers["google"]?.clientId))
                            .setIssuer("https://accounts.google.com")
                            .setAcceptableTimeSkewSeconds(1L)
                            .build()
                        val verifierOther = GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), JacksonFactory())
                            .setAudience(Arrays.asList(AuthConfig.providers["google"]?.clientId))
                            .setIssuer("accounts.google.com")
                            .setAcceptableTimeSkewSeconds(1L)
                            .build()
                        val idToken = verifierAndroid.verify(auth.idToken) ?: verifierOther.verify(auth.idToken)
                        if (idToken != null) {
                            val payload = idToken.payload
                            if (payload != null) {
                                val user = getOrRegister(payload)
                                call.sessions.set(SessionModel(Instant.now().plusSeconds(3600).toEpochMilli(), user))
                                call.respond(ResponseModel(MSG_LOGIN_SUCCESS, user))
                                return
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

            /**
             * Deletes the user's session
             */
            suspend fun logout(call: ApplicationCall) {
                call.sessions.clear<SessionModel>()
                call.respond(HttpStatusCode.NoContent)
            }

            /**
             * ROUTES
             */
            post("/login/google") { googleLogin(call) }
            post("/logout") { logout(call) }
        }
    }
}