package me.guillaumewilmot.api.gateway.models.exceptions

import io.ktor.http.HttpStatusCode
import me.guillaumewilmot.api.gateway.MSG_HTTP_403

class HttpForbiddenException : HttpException {
    @Suppress("unused")
    constructor() : this(MSG_HTTP_403)
    constructor(message: String) : super(HttpStatusCode.Forbidden, message)
}