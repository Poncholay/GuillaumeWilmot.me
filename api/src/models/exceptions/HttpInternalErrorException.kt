package me.guillaumewilmot.api.models.exceptions

import io.ktor.http.HttpStatusCode
import me.guillaumewilmot.api.MSG_HTTP_500

class HttpInternalErrorException : HttpException {
    constructor() : this(MSG_HTTP_500)
    constructor(message: String) : super(HttpStatusCode.InternalServerError, message)
}