package me.guillaumewilmot.api.gateway.controllers

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.*
import me.guillaumewilmot.api.gateway.MSG_HTTP_404
import me.guillaumewilmot.api.gateway.config.MicroserviceConfig
import me.guillaumewilmot.api.gateway.models.responses.ErrorResponseModel
import java.net.URL

@KtorExperimentalLocationsAPI
@Suppress("FunctionName")
object GatewayController {
    fun route(router: Route) {
        router.route("/{service}/{param...}") {

            /**
             * Redirects the call to the right microservice
             */
            suspend fun redirect(call: ApplicationCall, httpMethod: HttpMethod) {
                val service = call.parameters["service"]
                val param = call.parameters.getAll("param")?.joinToString("/")
                val port = MicroserviceConfig.services[service]?.port
                if (port != null) {
                    val client = HttpClient(Apache) {
                        expectSuccess = false
                    }
                    val response = client.request<HttpResponse> {
                        url(URL("http://localhost:$port/$param"))
                        method = httpMethod
                    }
                    return call.respond(response.status, response.readText())
                }
                call.respond(HttpStatusCode.NotFound, ErrorResponseModel(MSG_HTTP_404))
            }

            /**
             * Handlers
             */
            get { redirect(call, HttpMethod.Get) }
            post { redirect(call, HttpMethod.Post) }
            put { redirect(call, HttpMethod.Put) }
            delete { redirect(call, HttpMethod.Delete) }
            patch { redirect(call, HttpMethod.Patch) }
        }
    }
}