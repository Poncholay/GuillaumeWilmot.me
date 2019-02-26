package me.guillaumewilmot.api.gateway.models.exceptions

import io.ktor.http.HttpStatusCode
import me.guillaumewilmot.api.gateway.MSG_HTTP_500

class HttpInternalErrorException : HttpException {
    constructor() : this(MSG_HTTP_500)
    constructor(message: String) : super(HttpStatusCode.InternalServerError, message)
}